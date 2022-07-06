/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts;

import com.google.common.collect.Lists;
import de.monticore.TransformationScript;
import de.monticore.cd.codegen.CDGenerator;
import de.monticore.cd.codegen.CdUtilsPrinter;
import de.monticore.class2mc.Class2MCResolver;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.generating.templateengine.TemplateController;
import de.monticore.generating.templateengine.TemplateHookPoint;
import de.monticore.generating.templateengine.reporting.Reporting;
import de.monticore.generating.templateengine.reporting.commons.ASTNodeIdentHelper;
import de.monticore.generating.templateengine.reporting.commons.ReportManager;
import de.monticore.generating.templateengine.reporting.commons.ReportingRepository;
import de.monticore.generating.templateengine.reporting.reporter.*;
import de.monticore.io.paths.MCPath;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.sc2cd.SC2CDConverter;
import de.monticore.sc2cd.SC2CDConverterUMLV2;
import de.monticore.scbasis.BranchingDegreeCalculator;
import de.monticore.scbasis.InitialStateCollector;
import de.monticore.scbasis.ReachableStateCollector;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._cocos.*;
import de.monticore.scbasis._symboltable.SCStateSymbol;
import de.monticore.scevents._cocos.NonCapitalEventNames;
import de.monticore.scevents._cocos.NonCapitalParamNames;
import de.monticore.scevents._symboltable.SCEventsSTCompleter;
import de.monticore.scstatehierarchy.HierarchicalStateCollector;
import de.monticore.scstatehierarchy.NoSubstatesHandler;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.oosymbols.OOSymbolsMill;
import de.monticore.types.FullUMLStatechartsDeriver;
import de.monticore.types.check.FullSynthesizeFromMCBasicTypes;
import de.monticore.types.check.TypeCalculator;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.UMLStatechartsScopesGenitorDelegator;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.commons.cli.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.customizers.ImportCustomizer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Tool for processing UML StateCharts as defined by the 
 * UMLStatecharts language component:
 *  * starts the tooling and processes the parameters (main / run)
 *
 *  * read the input model (i.e. a StateChart)
 *  * and performs the required functionalities
 *
 *  * because the functions are relatively simple, they are all contained within
 *  this class 
 */
 
public class UMLStatechartsTool extends UMLStatechartsToolTOP {
  
  public static void main(String[] args) {
    UMLStatechartsTool tool = new UMLStatechartsTool();
    tool.init();
    tool.run(args);
  }

  /**
   * Contains the selected generation variant (option -var) 
   */
  protected String variant = "StatePattern1";
   
  /**
   * executes the tool by processing the arguments
   */
  @Override
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

      // we need the global scope for symbols and cocos
      MCPath symbolPath = new MCPath(Paths.get(""));
      if (cmd.hasOption("path")) {
        symbolPath = new MCPath(Arrays.stream(cmd.getOptionValues("path")).map(x -> Paths.get(x)).collect(Collectors.toList()));
      }
      UMLStatechartsMill.globalScope().setSymbolPath(symbolPath);
      BasicSymbolsMill.initializePrimitives();
      Class2MCResolver resolver = new Class2MCResolver();
      OOSymbolsMill.globalScope().addAdaptedOOTypeSymbolResolver(resolver);
      OOSymbolsMill.globalScope().addAdaptedTypeSymbolResolver(resolver);

      // parse input file, which is now available
      // (only returns if successful)
      String input = cmd.getOptionValue("i");
      ASTSCArtifact scartifact = parse(input);

      IUMLStatechartsArtifactScope scope = createSymbolTable(scartifact);

      // check context conditions
      runDefaultCoCos(scartifact);

      if (cmd.hasOption("t")) {
        doTrafos(scartifact, cmd.getOptionValues("t"));
      }

      if (cmd.hasOption("s")) {
        String path = cmd.getOptionValue("s", StringUtils.EMPTY);
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

      // -option variant
      if (cmd.hasOption("var")) {
        variant = cmd.getOptionValue("var", StringUtils.EMPTY);
      }

      // -option generate to CD
      if (cmd.hasOption("gen")) {
        String path = cmd.getOptionValue("gen");
        String configTemplate = cmd.getOptionValue("ct", StringUtils.EMPTY);
        String templatePath = cmd.getOptionValue("fp", StringUtils.EMPTY);
        String handcodedPath = cmd.getOptionValue("hcp", StringUtils.EMPTY);
        String reportDirectory = cmd.getOptionValue("genr", path + "/report");

        // Setup reporting
        ReportManager.ReportManagerFactory factory = getReportManagerFactory(path, reportDirectory);
        Reporting.init(path, reportDirectory, factory);

        String modelName = FilenameUtils.getBaseName(input);
        Reporting.on(modelName);
        Reporting.reportModelStart(scartifact, modelName, "");
        Reporting.reportParseInputFile(new File(input).getAbsoluteFile().toPath(), modelName);

        // Generate the CD
        generateCD(scartifact, path, configTemplate, templatePath, handcodedPath);

        // Flush reporting
        Reporting.reportModelEnd(modelName, "");
        Reporting.flush(scartifact);
      }

    } catch (ParseException e) {
      // an unexpected error from the apache CLI parser:
      Log.error("0xA5C02 Could not process CLI parameters: " + e.getMessage());
    }
  }

  protected ReportManager.ReportManagerFactory getReportManagerFactory(String outputDirectory, String reportDirectory) {
    return new ReportManager.ReportManagerFactory() {
      @Override public ReportManager provide(String modelName) {
        String lowerCaseName = modelName.toLowerCase();
        ReportManager reports = new ReportManager(outputDirectory);
        ReportingRepository repository = new ReportingRepository(new ASTNodeIdentHelper());
        repository.initAllTemplates();

        SummaryReporter summary = new SummaryReporter(reportDirectory, lowerCaseName, repository, UMLStatechartsMill.inheritanceTraverser());
        GeneratedFilesReporter generated = new GeneratedFilesReporter(reportDirectory, lowerCaseName, repository);
        HandWrittenCodeReporter handwritten = new HandWrittenCodeReporter(reportDirectory, lowerCaseName, repository);
        TemplatesReporter templates = new TemplatesReporter(reportDirectory, lowerCaseName, repository);
        TransformationReporter transformations = new TransformationReporter(reportDirectory, lowerCaseName, repository);
        SuccessfulReporter finishReporter = new SuccessfulReporter(reportDirectory, lowerCaseName);
        IncGenGradleReporter gradleReporter = new IncGenGradleReporter(reportDirectory, lowerCaseName);

        reports.addReportEventHandler(summary); // 01_Summary
        reports.addReportEventHandler(generated); // 02_GeneratedFiles
        reports.addReportEventHandler(handwritten); // 03_HandwrittenCodeFiles
        reports.addReportEventHandler(templates); // 04_Templates
        reports.addReportEventHandler(transformations); // 14_Transformations
        reports.addReportEventHandler(finishReporter); // 19_Successful
        reports.addReportEventHandler(gradleReporter);

        return reports;
      }
    };
  }

  /**
   * Creates the symbol table from the parsed AST.
   *
   * @param ast The top statechart model element.
   * @return The artifact scope derived from the parsed AST
   */
  @Override
  public IUMLStatechartsArtifactScope createSymbolTable(ASTSCArtifact ast) {

    // create scope and symbol skeleton
    UMLStatechartsScopesGenitorDelegator genitor = UMLStatechartsMill.scopesGenitorDelegator();
    IUMLStatechartsArtifactScope symTab = genitor.createFromAST(ast);

    // complete symbols including type check
    UMLStatechartsTraverser completer = UMLStatechartsMill.traverser();
    TypeCalculator typeCheck = new TypeCalculator(new FullSynthesizeFromMCBasicTypes(),new FullUMLStatechartsDeriver());
    completer.add4SCEvents(new SCEventsSTCompleter(typeCheck));
    ast.accept(completer);

    return symTab;
  }

  /**
   * Creates reports for the Statechart-AST to stdout or a specified file.
   *
   * @param scartifact The Statechart-AST for which the reports are created
   * @param path The target path of the directory for the report artifacts. If
   *          empty, the contents are printed to stdout instead
   */
  @Override
  public void report(ASTSCArtifact scartifact, String path) {
    // calculate and print reports
    String reachable = reportReachableStates(scartifact);
    print(reachable, path, REPORT_REACHABILITY);

    String branching = reportBranchingDegree(scartifact);
    print(branching, path, REPORT_BRANCHING_DEGREE);

    String stateNames = reportStateNames(scartifact);
    print(stateNames, path, REPORT_STATE_NAMES);
  }

  // names of the StateChart specific reports:
  public static final String REPORT_REACHABILITY = "reachability.txt";
  public static final String REPORT_BRANCHING_DEGREE = "branchingDegree.txt";
  public static final String REPORT_STATE_NAMES = "stateNames.txt";

  /* TODO #3093: from BR
     
- [ ] re-factor the analysis functionalities in 1 or 3 own classes
- [ ] re-factor `reportReachableStates` by extracting the individual collections for better re-usability
- [ ] re-factor `reportBranchingDegree` by extracting the individual collections for better re-usability


- [ ] check whether some of these analysis functionalities can be also applied into other variants of the SC language (i.e. with or without hierarchy, ...)
- [ ] if analysis technique is not generally applicable: make variants e.g. through subclasses;  assign individual analysis techniques to Individual language components (through mlc files)

--> the later is a good demonstration for modularity of analysis techniques

   */
   
  /**
   * Calculates the reachable states of a Statechart
   * into a human-readable string (as a report).
   * 
   * This function can be used as a blueprint for certain forms of
   * Analysis techniques on StateCharts, even though more efficient
   * realizations would be possible, when abstracting the AST 
   * to some kind of graph structure essence, or creating an additional
   * graph structure linkage for efficient navigation.
   *
   * @param ast The Statechart-AST for which the report is created
   */
  public String reportReachableStates(ASTSCArtifact ast) {
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();

    // collect all states using a visitor
    // HierarchicalStateCollector vs StateCollector
    HierarchicalStateCollector stateCollector = new HierarchicalStateCollector();
    traverser.add4SCBasis(stateCollector);
    traverser.add4SCStateHierarchy(stateCollector);
    ast.accept(traverser);
    
    Set<String> statesToBeChecked = stateCollector.getStates()
      .stream().map(e -> e.getName()).collect(Collectors.toSet());

    // collect all initial states
    traverser = UMLStatechartsMill.traverser();
    InitialStateCollector initialStateCollector = new InitialStateCollector();
    traverser.add4SCBasis(initialStateCollector);
    //  only find top-level initial states (but not within substates)
    traverser.setSCStateHierarchyHandler(new NoSubstatesHandler());
    ast.accept(traverser);
    Set<String> reachableStates = initialStateCollector.getStates();

    // calculate reachable states
    Set<String> currentlyChecked = new HashSet<>(reachableStates);
    statesToBeChecked.removeAll(reachableStates);

    while (!currentlyChecked.isEmpty()) {

      // While the open list is not empty, check which states can be reached from it
      String from = currentlyChecked.iterator().next();
      currentlyChecked.remove(from);
      ReachableStateCollector reachableStateCollector = new ReachableStateCollector(from);
      traverser = UMLStatechartsMill.traverser();
      traverser.add4SCBasis(reachableStateCollector);
      ast.accept(traverser);

      for (String to : reachableStateCollector.getReachableStates()) {
        if (!reachableStates.contains(to)) {
          // In case a new reachable state is found, add it to the open list
          // and mark it as reachable
          reachableStates.add(to);
          currentlyChecked.add(to);
          statesToBeChecked.remove(to);
        }
      }

      // Handle all inner initial states
      Optional<SCStateSymbol> stateSymbol = ast.getEnclosingScope().resolveSCState(from);
      stateCollector.getStatesMap().clear();
      traverser.add4SCBasis(stateCollector);
      traverser.add4SCStateHierarchy(stateCollector);
      if (!stateSymbol.isPresent()) {
        throw new IllegalStateException("0xDD476 Failed to resolve state symbol " + from);
      }
      stateSymbol.get().getAstNode().accept(traverser);
      
      for (ASTSCState innerReachableState : stateCollector.getStates(1)) {
        if (innerReachableState.getSCModifier().isInitial()) {
          reachableStates.add(innerReachableState.getName());
          currentlyChecked.add(innerReachableState.getName());
          statesToBeChecked.remove(innerReachableState.getName());
        }
      }

    }
    return "reachable: " + String.join(",", reachableStates) + System.lineSeparator()
      + "unreachable: " + String.join(",", statesToBeChecked) + System.lineSeparator() ;
  }


  /**
   * Calculates the branching degree of each state of a Statechart
   * into a human-readable string (as a report).
   * 
   * @param ast The Statechart-AST for which the report is created
   */
  public String reportBranchingDegree(ASTSCArtifact ast) {
    // calculate using a visitor
    BranchingDegreeCalculator branchingDegreeCalculator = new BranchingDegreeCalculator();    
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(branchingDegreeCalculator);
    ast.accept(traverser);
    
    return branchingDegreeCalculator.getBranchingDegrees().entrySet().stream()
      .map(e -> e.getKey() + ": " + e.getValue())
      .collect(Collectors.joining(System.lineSeparator())) + System.lineSeparator();
  }

  /**
   * Collects the state names of a Statechart
   * into a human-readable string (as a report).
   * 
   * @param ast The Statechart-AST for which the report is created
   */
  public String reportStateNames(ASTSCArtifact ast) {
    // collect using a visitor
    StateCollector stateCollectorVisitor = new StateCollector();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(stateCollectorVisitor);
    ast.accept(traverser);

    return String.join(", ", stateCollectorVisitor.getStates()
      .stream().map(e -> e.getName()).collect( Collectors.toSet())) + System.lineSeparator();
  }


  /**
   * Checks whether ast satisfies all desired CoCos.
   *
   * @param ast The ast of the SC.
   */
  @Override
  public void runDefaultCoCos(ASTSCArtifact ast) {
    // instantiate the CoCo checker infrastructure 
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();

    // add all individual CoCo's to be checked
    checker.addCoCo(new UniqueStates());
    checker.addCoCo(new TransitionSourceTargetExists());
    UMLStatechartsTraverser t = UMLStatechartsMill.traverser();
    t.setSCStateHierarchyHandler(new NoSubstatesHandler());
    checker.addCoCo(new AtLeastOneInitialState(t));
    checker.addCoCo(new CapitalStateNames());
    checker.addCoCo(new PackageCorrespondsToFolders());
    checker.addCoCo(new SCFileExtension());
    checker.addCoCo(new SCNameIsArtifactName());
    checker.addCoCo(new NonCapitalEventNames());
    checker.addCoCo(new NonCapitalParamNames());

    // execute the CoCo's
    checker.checkAll(ast);
  }


  /**
   * Apply transformation groovy workflow scripts
   * @param ast The SC-AST to be transformed
   * @param trafoScripts the array of trafos groovy script to be applied
   */
  public void doTrafos(ASTSCArtifact ast, String[] trafoScripts) {
  
    CompilerConfiguration config = new CompilerConfiguration();
    // Groovy base script providing trafo helpers
    config.setScriptBaseClass(TransformationScript.class.getName()); 
    
    // By default, import all trafos from the default de.monticore.tf package
    config.addCompilationCustomizers(new ImportCustomizer().addStarImports("de.monticore.tf"));

    GroovyShell shell = new GroovyShell(this.getClass().getClassLoader(), new Binding(), config);

    for (String script : trafoScripts) {
      try {
        shell.getContext().setVariable("ast", ast);
        TransformationScript groovyScript = (TransformationScript) shell.parse(new File(script));
        groovyScript.__setTransformationGroovyShell(shell); // Pass this shell to respect imports
        groovyScript.run();
      }catch (IOException e){
        throw new RuntimeException(e.getMessage(), e.getCause());
      }
    }
  }

  /**
   * Prints the contents of the SC-AST to stdout or a specified file.
   *
   * @param scartifact The SC-AST to be pretty printed
   * @param file The target file name for printing the SC artifact. If empty,
   *          the content is printed to stdout instead
   */
  @Override
  public void prettyPrint(ASTSCArtifact scartifact, String file) {
    // pretty print AST
    UMLStatechartsFullPrettyPrinter prettyPrinterDelegator
      = new UMLStatechartsFullPrettyPrinter();
    String prettyOutput = prettyPrinterDelegator.prettyprint(scartifact);
    print(prettyOutput, file);
  }

  public void print(String content, String path, String file) {
    print(content, path.isEmpty()?path : path + "/"+ file);
  }


  /**
   * Prints the contents of the SD-AST to stdout or
   * generates the java classes for the generated SD-AST into a directory
   *
   * @param scartifact The SC-AST to be converted
   * @param outputDirectory The target directory name for outputting the CD artifact. If empty,
   *          the content is printed to stdout instead
   */
  public void generateCD(ASTSCArtifact scartifact,
                         String outputDirectory,
                         String configTemplate,
                         String templatePath,
                         String handcodedPath) {

    GeneratorSetup setup = new GeneratorSetup();
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    setup.setGlex(glex);
    if (!handcodedPath.isEmpty()) {
      setup.setHandcodedPath(new MCPath(handcodedPath));
    }
    if (!templatePath.isEmpty()) {
      setup.setAdditionalTemplatePaths(Lists.newArrayList(new File(templatePath)));
    }
    glex.setGlobalValue("cdPrinter", new CdUtilsPrinter());

    if (!outputDirectory.isEmpty()){
      // Prepare CD4C
      File targetDir = new File(outputDirectory);
      if (!targetDir.exists())
        targetDir.mkdirs();
      setup.setOutputDirectory(targetDir);
      // optionally: setup.setTracing(false);
    }

    //
    configTemplate = configTemplate.isEmpty()?"de.monticore.sc2cd.SC2CD":configTemplate;
    TemplateController tc = setup.getNewTemplateController(configTemplate);
    CDGenerator generator = new CDGenerator(setup);
    TemplateHookPoint hpp = new TemplateHookPoint(configTemplate);
    List<Object> configTemplateArgs;
    // select the conversion variant:
    switch(variant) {
      case "StatePattern1":
        // default converter:
        SC2CDConverter converter = new SC2CDConverter();
        configTemplateArgs = Arrays.asList(glex, converter, setup.getHandcodedPath(), generator);
        break;
      case "StatePattern2":
        // enhanced converter:
        SC2CDConverterUMLV2 converter2 = new SC2CDConverterUMLV2();
        configTemplateArgs = Arrays.asList(glex, converter2, setup.getHandcodedPath(), generator);
        break;
      default:
        Log.error("0xCC742 Illegal generator variant '"+variant+"' selected. Aborting.");
        SC2CDConverter c0 = new SC2CDConverter();   // dummy, only reached when error flag is Off
        configTemplateArgs = Arrays.asList(glex, c0, setup.getHandcodedPath(), generator);
        break;
    }

    hpp.processValue(tc, scartifact, configTemplateArgs);

  }

  /**
   * Initializes the standard options for the Statechart tool.
   *
   * @return The CLI options with arguments.
   */
  @Override
  public Options addStandardOptions(Options options) {
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
    options.addOption(Option.builder("s")
      .longOpt("symboltable")
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

    // model paths
    options.addOption(Option.builder("path")
      .hasArgs()
      .desc("Sets the artifact path for imported symbols, space separated.")
      .build());

    return options;
  }

  /**
   * Initializes the additional options for the Statechart tool.
   *
   * @return The CLI options with arguments.
   */
  @Override
  public Options addAdditionalOptions(Options options) {
    // convert to state pattern CD
    options.addOption(Option.builder("gen")
        .longOpt("generate")
        .argName("dir")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("Prints the state pattern CD-AST to stdout or the generated java classes to the specified folder (optional)")
        .build());

    options.addOption(Option.builder("var")
        .longOpt("variant")
        .argName("name")
        .optionalArg(false)
        .numberOfArgs(1)
        .desc("Choose the generation variant (possible e.g.: StatePattern1 (default), StatePattern2)")
        .build());

    // configTemplate parameter
    options.addOption(Option.builder("ct")
        .longOpt("configTemplate")
        .argName("file")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("Provides a config template (optional)")
        .build());

    // handcoded gen path parameter
    options.addOption(Option.builder("hcp")
        .longOpt("handcodedPath")
        .argName("pathlist")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("List of directories to look for handwritten code to integrate (optional)")
        .build());


    // templatePath parameter
    options.addOption(Option.builder("fp")
        .longOpt("templatePath")
        .argName("pathlist")
        .optionalArg(true)
        .numberOfArgs(1)
        .desc("List of directories to look for handwritten templates to integrate (optional)")
        .build());

    // model paths
    options.addOption(Option.builder("path")
        .argName("pathlist")
        .hasArgs()
        .desc("Sets the artifact path for imported symbols, space separated.")
        .build());

    // trafo script parameter
    options.addOption(Option.builder("t")
                              .longOpt("trafo")
                              .argName("groovyscript")
                              .hasArgs()
                              .desc("Specifies the path for a groovy script applying transformations (optional)")
                              .build());

    // specify generate reports path
    options.addOption(Option.builder("genr")
                              .longOpt("genreport")
                              .argName("dir")
                              .hasArgs()
                              .desc("Specifies the directory for printing reports about the given model.")
                              .build());

    return options;
  }
}
