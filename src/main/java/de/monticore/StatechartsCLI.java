/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.io.FileReaderWriter;
import de.monticore.scbasis.InitialStateCollectorVisitor;
import de.monticore.scbasis.ReachableStateVisitor;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scstatehierarchy.HierarchicalBranchingDegree;
import de.monticore.scstatehierarchy.HierarchicalStateCollector;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.*;
import de.monticore.umlstatecharts._visitor.UMLStatechartsDelegatorVisitor;
import de.monticore.utils.Names;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.cli.*;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StatechartsCLI {

  /**
   * Main method that is called from command line and runs the UML Statechart tool.
   *
   * @param args The input parameters for configuring the UML Statechart tool.
   */
  public static void main(String[] args) {
    StatechartsCLI cli = new StatechartsCLI();
    // initialize logging with standard logging
    Log.init();
    UMLStatechartsMill.init();
    cli.run(args);
  
  }
  
  public void run(String[] args){
    Options options = initOptions();
  
    try {
      // create CLI parser and parse input options from command line
      CommandLineParser cliparser = new DefaultParser();
      CommandLine cmd = cliparser.parse(options, args);
    
      // help: when --help
      if (cmd.hasOption("h")) {
        printHelp(options);
        // do not continue, when help is printed
        return;
      }
    
      // if -i input is missing: also print help and stop
      if (!cmd.hasOption("i")) {
        printHelp(options);
        // do not continue, when help is printed
        return;
      }
    
      // parse input file, which is now available
      // (only returns if successful)
      ASTSCArtifact scartifact = parseFile(cmd.getOptionValue("i"));
  
      IUMLStatechartsArtifactScope scope = createSymbolTable(scartifact);
      String fileName = Paths.get(cmd.getOptionValue("i")).getFileName().toString();
      scope.setName(fileName.substring(0, fileName.lastIndexOf('.')));
  
      if (cmd.hasOption("st")) {
        String path = cmd.getOptionValue("st", StringUtils.EMPTY);
        storeSymbols(scope, path);
      }
    
      // -option pretty print
      if (cmd.hasOption("pp")) {
        String path = cmd.getOptionValue("pp", StringUtils.EMPTY);
        prettyPrint(scartifact, path);
      }
    
      // -option reports
      if (cmd.hasOption("r")) {
        String path = cmd.getOptionValue("r", StringUtils.EMPTY);
        report(scartifact, path);
      }
    
    
    } catch (ParseException e) {
      // ann unexpected error from the apache CLI parser:
      Log.error("0xA5C01 Could not process CLI parameters: " + e.getMessage());
    }
  }
  
  public void storeSymbols(IUMLStatechartsArtifactScope scope, String path) {
    UMLStatechartsScopeDeSer deser = new UMLStatechartsScopeDeSer();
    String serialized = deser.serialize(scope);
    Path f = Paths.get(path)
        .resolve(Paths.get(Names.getPathFromPackage(scope.getPackageName())))
        .resolve(scope.getName()+".scsym");
    FileReaderWriter.storeInFile(f, serialized);
  }
  
  /**
   * Creates the symbol table from the parsed AST.
   *
   * @param ast The top statechart model element.
   * @return The artifact scope derived from the parsed AST
   */
  public IUMLStatechartsArtifactScope createSymbolTable(ASTSCArtifact ast) {
    IUMLStatechartsGlobalScope globalScope = UMLStatechartsMill.globalScope();
    globalScope.setFileExt(".sc");
  
    UMLStatechartsScopesGenitorDelegator genitor = UMLStatechartsMill.scopesGenitorDelegator();

    return genitor.createFromAST(ast);
  }
  
  /**
   * Creates reports for the Statechart-AST to stdout or a specified file.
   *
   * @param scartifact The Statechart-AST for which the reports are created
   * @param path The target path of the directory for the report artifacts. If
   *          empty, the contents are printed to stdout instead
   */
  public void report(ASTSCArtifact scartifact, String path) {
    // calculate and print reports
    String reachable = reportReachableStates(scartifact);
    print(reachable, path, REPORT_REACHABILITY);

    String branching = reportBranchingDegree(scartifact);
    print(branching, path, REPORT_BRANCHING_DEGREE);

    String stateNames = reportStateNames(scartifact);
    print(stateNames, path, REPORT_STATE_NAMES);
  }

  // names of the reports:
  public static final String REPORT_REACHABILITY = "reachability.txt";
  public static final String REPORT_BRANCHING_DEGREE = "branchingDegree.txt";
  public static final String REPORT_STATE_NAMES = "stateNames.txt";

  public String reportReachableStates(ASTSCArtifact ast) {
    UMLStatechartsDelegatorVisitor delegator = UMLStatechartsMill.uMLStatechartsDelegatorVisitorBuilder().build();
    // collect all states
    HierarchicalStateCollector stateCollector = new HierarchicalStateCollector();
    delegator.setSCBasisVisitor(stateCollector);
    delegator.setSCStateHierarchyVisitor(stateCollector);
    ast.accept(delegator);
    Set<String> statesToBeChecked = stateCollector.getStates()
        .stream().map(e -> e.getName()).collect(Collectors.toSet());
    
    // collect all initial states
    InitialStateCollectorVisitor initialStateCollectorVisitor = new InitialStateCollectorVisitor();
    delegator.setSCBasisVisitor(initialStateCollectorVisitor);
    ast.accept(delegator);
    Set<String> reachableStates = initialStateCollectorVisitor.getStates();
    
    // calculate reachable states
    Set<String> currentlyChecked = new HashSet<>(reachableStates);
    statesToBeChecked.removeAll(reachableStates);
    while (!currentlyChecked.isEmpty()) {
      // While the open list is not empty, check which states can be reached from it
      String from = currentlyChecked.iterator().next();
      currentlyChecked.remove(from);
      ReachableStateVisitor reachableStateVisitor = new ReachableStateVisitor(from);
      delegator.setSCBasisVisitor(reachableStateVisitor);
      ast.accept(delegator);
      for (String to : reachableStateVisitor.getReachableStates()) {
        if (!reachableStates.contains(to)) {
          // In case a new reachable state is found, add it to the open list
          // and mark it as reachable
          reachableStates.add(to);
          currentlyChecked.add(to);
          statesToBeChecked.remove(to);
        }
      }
    }
    return "reachable: " + String.join(",", reachableStates) + System.lineSeparator() + "unreachable: " + String.join(",", statesToBeChecked);
  }

  public String reportBranchingDegree(ASTSCArtifact ast) {
    HierarchicalBranchingDegree branchingDegreeVisitor = new HierarchicalBranchingDegree();
    UMLStatechartsDelegatorVisitor delegator = UMLStatechartsMill.uMLStatechartsDelegatorVisitorBuilder().build();
    delegator.setSCBasisVisitor(branchingDegreeVisitor);
    delegator.setSCStateHierarchyVisitor(branchingDegreeVisitor);
    ast.accept(delegator);
    return branchingDegreeVisitor.getBranchingDegrees().entrySet().stream()
        .map(e -> e.getKey() + ": " + e.getValue())
        .collect(Collectors.joining(System.lineSeparator()));
  }

  public String reportStateNames(ASTSCArtifact ast) {
    HierarchicalStateCollector stateCollectorVisitor = new HierarchicalStateCollector();
    UMLStatechartsDelegatorVisitor delegator = UMLStatechartsMill.uMLStatechartsDelegatorVisitorBuilder().build();
    delegator.setSCBasisVisitor(stateCollectorVisitor);
    delegator.setSCStateHierarchyVisitor(stateCollectorVisitor);
    ast.accept(delegator);
    ast.accept(stateCollectorVisitor);
    return String.join(", ", stateCollectorVisitor.getStates()
        .stream().map(e -> e.getName()).collect( Collectors.toSet()));
  }
  
  /**
   * Formats and prints the help information including parameters an options.
   *
   * @param options The input parameters and options.
   */
  public void printHelp(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.setWidth(80);
    formatter.printHelp("UMLSCTool", options);
  }
  
  /**
   * Parses the contents of a given file as a Statechart.
   *
   * @param path The path to the Statechart-file as String
   */
  public ASTSCArtifact parseFile(String path) {
    Optional<ASTSCArtifact> sc = Optional.empty();
    try {
      Path model = Paths.get(path);
      UMLStatechartsParser parser = new UMLStatechartsParser();
      sc = parser.parse(model.toString());
    }
    catch (IOException | NullPointerException e) {
      Log.error("0xA5C02 Input file " + path + " not found.");
    }
    return sc.get();
  }
  
  /**
   * Prints the contents of the SC-AST to stdout or a specified file.
   *
   * @param scartifact The SC-AST to be pretty printed
   * @param file The target file name for printing the SC artifact. If empty,
   *          the content is printed to stdout instead
   */
  public void prettyPrint(ASTSCArtifact scartifact, String file) {
    // pretty print AST
//    UMLStatechartsFullPrettyPrinter prettyPrinterDelegator
//        = new UMLStatechartsFullPrettyPrinter();
//    scartifact.accept(prettyPrinterDelegator);
//    String prettyOutput = prettyPrinterDelegator.getPrinter().getContent();
//    print(prettyOutput, file);
  }

  public void print(String content, String path, String file) {
    print(content, path.isEmpty()?path : path + "/"+ file);
  }

  /**
   * Prints the given content to a target file (if specified) or to stdout (if
   * the file is Optional.empty()).
   *
   * @param content The String to be printed
   * @param path The target path to the file for printing the content. If empty,
   *          the content is printed to stdout instead
   */
  public void print(String content, String path) {
    // print to stdout or file
    if (path.isEmpty()) {
      System.out.println(content);
    } else {
      File f = new File(path);
      // create directories (logs error otherwise)
      f.getAbsoluteFile().getParentFile().mkdirs();

      FileWriter writer;
      try {
        writer = new FileWriter(f);
        writer.write(content);
        writer.close();
      } catch (IOException e) {
        Log.error("0xA7105 Could not write to file " + f.getAbsolutePath());
      }
    }
  }



  /**
   * Initializes the available CLI options for the Statechart tool.
   *
   * @return The CLI options with arguments.
   */
  protected Options initOptions() {
    Options options = new Options();
    
    // help dialog
    options.addOption(Option.builder("h")
        .longOpt("help")
        .desc("Prints this help dialog")
        .build());
    
    // parse input file
    options.addOption(Option.builder("i")
        .longOpt("input")
        .argName("file")
        .hasArg()
        .desc("Reads the source file (mandatory) and parses the contents as a statechart")
        .build());
    
    // pretty print SC
    options.addOption(Option.builder("pp")
        .longOpt("prettyprint")
        .argName("file")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("Prints the Statechart-AST to stdout or the specified file (optional)")
        .build());
  
    // pretty print SC
    options.addOption(Option.builder("st")
        .longOpt("store")
        .argName("file")
        .hasArg()
        .desc("Serialized the Symbol table of the given Statechart")
        .build());
    
    // reports about the SC
    options.addOption(Option.builder("r")
        .longOpt("report")
        .argName("dir")
        .hasArg(true)
        .desc("Prints reports of the statechart artifact to the specified directory. Available reports:"
            + System.lineSeparator() + "reachable states, branching degree, and state names")
        .build());
    
    return options;
  }
}
