/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore._visitor.BranchingDegreeVisitor;
import de.monticore._visitor.InitialStateCollectorVisitor;
import de.monticore._visitor.ReachableStateVisitor;
import de.monticore._visitor.StateNameCollectorVisitor;
import de.monticore.io.paths.ModelPath;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsGlobalScope;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsSymbolTableCreatorDelegator;
import de.se_rwth.commons.Files;
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
    
      createSymbolTable(scartifact);
    
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
  
  
  
  /**
   * Creates the symbol table from the parsed AST.
   *
   * @param ast The top statechart model element.
   * @return The artifact scope derived from the parsed AST
   */
  public UMLStatechartsArtifactScope createSymbolTable(ASTSCArtifact ast) {
    UMLStatechartsGlobalScope globalScope = UMLStatechartsMill.uMLStatechartsGlobalScopeBuilder()
        .setModelPath(new ModelPath())
        .setModelFileExtension(".sc")
        .build();
    
    UMLStatechartsSymbolTableCreatorDelegator symbolTable = UMLStatechartsMill.uMLStatechartsSymbolTableCreatorDelegatorBuilder()
        .setGlobalScope(globalScope)
        .build();
    
    return symbolTable.createFromAST(ast);
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
    StateNameCollectorVisitor stateCollectorVisitor = new StateNameCollectorVisitor();
    ast.accept(stateCollectorVisitor);
    Set<String> unreachableStates = stateCollectorVisitor.getStates();
    InitialStateCollectorVisitor initialStateCollectorVisitor = new InitialStateCollectorVisitor();
    Set<String> reachableStates = initialStateCollectorVisitor.getStates();
    Set<String> openList = new HashSet<>(reachableStates);
    unreachableStates.removeAll(reachableStates);
    while (!openList.isEmpty()) {
      // While the open list is not empty, check which states can be reached from it
      String from = openList.iterator().next();
      openList.remove(from);
      ReachableStateVisitor reachableStateVisitor = new ReachableStateVisitor(from);
      ast.accept(reachableStateVisitor);
      for (String to : reachableStateVisitor.getReachableStates()) {
        if (!reachableStates.contains(to)) {
          // In case a new reachable state is found, add it to the open list
          // and mark it as reachable
          reachableStates.add(to);
          openList.add(to);
          unreachableStates.remove(to);
        }
      }
    }
    return "reachable: " + String.join(",", reachableStates) + System.lineSeparator() + "unreachable: " + String.join(",", unreachableStates);
  }

  public String reportBranchingDegree(ASTSCArtifact ast) {
    BranchingDegreeVisitor branchingDegreeVisitor = new BranchingDegreeVisitor();
    ast.accept(branchingDegreeVisitor);
    return branchingDegreeVisitor.getBranchingDegrees().entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining(System.lineSeparator()));
  }

  public String reportStateNames(ASTSCArtifact ast) {
    StateNameCollectorVisitor stateCollectorVisitor = new StateNameCollectorVisitor();
    ast.accept(stateCollectorVisitor);
    return String.join(", ", stateCollectorVisitor.getStates());
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
    UMLStatechartsPrettyPrinterDelegator prettyPrinterDelegator
        = new UMLStatechartsPrettyPrinterDelegator();
    scartifact.accept(prettyPrinterDelegator);
    String prettyOutput = prettyPrinterDelegator.getPrinter().getContent();
    print(prettyOutput, file);
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
    
    // reports about the SC
    options.addOption(Option.builder("r")
        .longOpt("report")
        .argName("dir")
        .hasArg(true)
        .desc("Prints reports of the statechart artifact to the specified directory (optional). Available reports:"
            + System.lineSeparator() + "reachable states, branching degree, and state names")
        .build());
    
    return options;
  }
}
