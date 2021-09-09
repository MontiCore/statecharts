/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.triggeredstatecharts.TriggeredStatechartsCLI;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._symboltable.ITriggeredStatechartsArtifactScope;
import de.monticore.triggeredstatecharts._symboltable.ITriggeredStatechartsGlobalScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TriggeredStatechartsCLITest {

  String resourcesDir = "src/test/resources/";
  String outputDir = "target/tooltest/";

  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.initPlusLog();
    TriggeredStatechartsMill.init();
  }

  @After
  public void after(){
    CD4CodeMill.reset();
  }

  @Before
  public void setUp() {
    Log.clearFindings();
    TriggeredStatechartsMill.init();
    ITriggeredStatechartsGlobalScope gs = TriggeredStatechartsMill.globalScope();
    gs.clear();
  }

  @Test
  public void testTriggeredStatecharts(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc"
    });
    assertEquals("Door3.sc was not processed successfully", Log.getErrorCount(), 0);
  }

  @Test
  public void testTriggeredStatechartsPP(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-pp"
    });
    assertEquals("Pretty printing of Door3.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testTriggeredStatechartsPP2(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "tf/Example.sc",
      "-pp"
    });
    assertEquals("Pretty printing of Example.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsStore(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-s", outputDir + "door3/Door3.scsym"
    });
    assertEquals("Storing symbol table of Door3.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsStore2(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-s", outputDir + "car2/Car2.scsym"
    });
    assertEquals("Storing symbol table of Car2.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsStore3(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "tf/Example.sc",
      "-s", outputDir + "testsc/Example.scsym"
    });
    assertEquals("Storing symbol table of Example.sc was not successful", Log.getErrorCount(), 0);
  }


  @Test
  public void testUMLStatechartsPP3(){
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-pp", outputDir + "testsc/Car2.sc"
    });
    Log.getFindings().forEach(System.out::println);
    assertEquals("Pretty printing Car2.sc was not successful", Log.getErrorCount(), 0);
  }


  @Test
  public void testUMLStatechartsReportDoor() throws IOException {
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-r", outputDir + "door3"
    });
    assertEquals("Reporting for Door3.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "door3/branchingDegree.txt"));
    assertEquals("Branching Degree of Opened", Integer.valueOf(1), branchingDegree.getOrDefault("Opened", -1));
    assertEquals("Branching Degree of Closed", Integer.valueOf(2), branchingDegree.getOrDefault("Closed", -1));
    assertEquals("Branching Degree of Locked", Integer.valueOf(1), branchingDegree.getOrDefault("Locked", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "door3/reachability.txt"));
    assertEquals("Reachability of Closed", "reachable", reachability.get("Closed"));
    assertEquals("Reachability of Opened", "reachable", reachability.get("Opened"));
    assertEquals("Reachability of Locked", "reachable", reachability.get("Locked"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "door3/stateNames.txt"));
    for (String state : Arrays.asList("Closed", "Opened", "Locked"))
      assertTrue("StateNames " + state, stateNames.contains(state));
  }

  @Test
  public void testUMLStatechartsReportCar() throws IOException{
    new TriggeredStatechartsCLI().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-r", outputDir + "/car2"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "car2/branchingDegree.txt"));
    assertEquals("Branching Degree of EngineOff", Integer.valueOf(1), branchingDegree.getOrDefault("EngineOff", -1));
    assertEquals("Branching Degree of EngineRunning", Integer.valueOf(1), branchingDegree.getOrDefault("EngineRunning", -1));
    assertEquals("Branching Degree of Parking", Integer.valueOf(0), branchingDegree.getOrDefault("Parking", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "car2/reachability.txt"));
    assertEquals("Reachability of EngineOff", "reachable", reachability.get("EngineOff"));
    assertEquals("Reachability of EngineRunning", "reachable", reachability.get("EngineRunning"));
    assertEquals("Reachability of Driving", "unreachable", reachability.get("Driving"));
    assertEquals("Reachability of Parking", "reachable", reachability.get("Parking"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "car2/stateNames.txt"));
    for (String state : Arrays.asList("EngineOff", "EngineRunning", "Driving", "Parking"))
      assertTrue("StateNames " + state, stateNames.contains(state));

  }

  /**
   * Loads the generated branching degree report into a map
   * Each state name is mapped to its branching degree
   * @param file the path to the reports text file
   * @return a map with the branching degree report
   */
  private Map<String, Integer> loadBranchingDegree(File file)
    throws IOException {
    assertTrue("branchingDegree report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
      .map(l -> l.split(":", 2))
      .collect(Collectors.toMap(e -> e[0], e -> Integer.parseInt(e[1].trim())));
  }

  /**
   * Loads the generated reachability report into a map
   * Each state name is mapped to either reachable or unreachable
   * @param file the path to the reports text file
   * @return a map with the reachability report
   */
  private Map<String, String> loadReachability(File file)
    throws IOException {
    assertTrue("reachability report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
      .map(l -> l.split(":", 2)) // Split (un)reachable -> S1,S2
      .map(e -> splitCommaSeparatedStream(e[1], e[0].trim()))
      .flatMap(Stream::unordered)
      .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  /**
   * Helper method which splits a comma separated set of keys
   * @param keys comma separated keys
   * @param value the value
   * @return a stream of map entries of each key with the value
   */
  private Stream<Map.Entry<String, String>> splitCommaSeparatedStream(String keys, String value){
    return Stream.of(keys.split(","))
      .map(String::trim)
      .map(k -> new AbstractMap.SimpleImmutableEntry<>(k, value));
  }

  /**
   * Loads the generated state names report into a set
   * @param file the path to the reports text file
   * @return a set with state names report
   */
  private Set<String> loadStateNames(File file)
    throws IOException {
    assertTrue("stateNames report missing", file.exists());
    return Files.readAllLines(file.toPath()).stream()
      .map(l -> Stream.of(l.split(",")))
      .flatMap(Stream::unordered)
      .map(String::trim)
      .collect(Collectors.toSet());
  }

  @Test
  public void testHelp(){
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));
    new TriggeredStatechartsCLI().run(new String[]{    "-h" });
    assertEquals(Log.getErrorCount(), 0);
    String result = out.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
    assertEquals( "usage: TriggeredStatechartsCLI\n" +
        " -ct,--configTemplate <file>       Provides a config template (optional)\n" +
        " -fp,--templatePath <pathlist>     List of directories to look for handwritten\n" +
        "                                   templates to integrate (optional)\n" +
        " -h,--help                         Prints this help dialog\n" +
        " -hcp,--handcodedPath <pathlist>   List of directories to look for handwritten\n" +
        "                                   code to integrate (optional)\n" +
        " -i,--input <file>                 Reads the source file (mandatory) and parses\n" +
        "                                   the contents as a statechart\n" +
        " -path <pathlist>                  Sets the artifact path for imported symbols,\n" +
        "                                   space separated.\n" +
        " -pp,--prettyprint <file>          Prints the Statechart-AST to stdout or the\n" +
        "                                   specified file (optional)\n" +
        " -r,--report <dir>                 Prints reports of the statechart artifact to\n" +
        "                                   the specified directory. Available reports:\n" +
        "                                   reachable states, branching degree, and state\n" +
        "                                   names\n" +
        " -s,--symboltable <file>           Serialized the Symbol table of the given\n" +
        "                                   Statechart\n"
      , result );
  }

}
