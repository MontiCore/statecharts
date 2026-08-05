/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.triggeredstatecharts.TriggeredStatechartsTool;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._symboltable.ITriggeredStatechartsGlobalScope;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriggeredStatechartsToolTest extends GeneralAbstractTest{

  String resourcesDir = "src/test/resources/";
  String outputDir = "target/tooltest/";

  @AfterEach
  public void after(){
    CD4CodeMill.reset();
  }

  @Override
  @BeforeEach
  public void setUp() {
    initLogger();
    initTriggeredStatechartsMill();
    ITriggeredStatechartsGlobalScope gs = TriggeredStatechartsMill.globalScope();
    gs.clear();
  }

  @Test
  public void testTriggeredStatecharts(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc"
    });
    assertEquals(Log.getErrorCount(), 0, "Door3.sc was not processed successfully");
  }

  @Test
  public void testTriggeredStatechartsPP(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-pp"
    });
    assertEquals(Log.getErrorCount(), 0, "Pretty printing of Door3.sc was not successful");
  }

  @Test
  public void testTriggeredStatechartsPP2(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "tf/Example.sc",
      "-pp"
    });
    assertEquals(Log.getErrorCount(), 0, "Pretty printing of Example.sc was not successful");
  }

  @Test
  public void testTriggeredStatechartsConverter(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/DoorExample2.sc",
      "-gen", "target/gentest4"
    });
    assertEquals(Log.getErrorCount(), 0, "Converting to SD of DoorExample2.sc was not successful");
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testTriggeredStatechartsConverterWithConfigTemplate(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/DoorExample2.sc",
      "-gen", "target/gentest5",
      "-fp", "src/test/resources",
      "-ct", "configTemplate/ct.ftl"
    });
    assertEquals(Log.getErrorCount(), 0, "Converting to CD of DoorExample2.sc was not successful");
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testTriggeredStatechartsConverterWithConfigTemplateAndTOP(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/DoorExample2.sc",
      "-gen", "target/gentest6",
      "-fp", "src/test/resources",
      "-ct", "configTemplate/ct.ftl",
      "-hcp", "src/test/resources/handcoded"
    });
    assertEquals(Log.getErrorCount(), 0, "Converting to CD of DoorExample2.sc was not successful");
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testTriggeredStatechartsStore(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-s", outputDir + "door3/Door3.scsym"
    });
    assertEquals(Log.getErrorCount(), 0, "Storing symbol table of Door3.sc was not successful");
  }

  @Test
  public void testTriggeredStatechartsStore2(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-s", outputDir + "car2/Car2.scsym"
    });
    assertEquals(Log.getErrorCount(), 0, "Storing symbol table of Car2.sc was not successful");
  }

  @Test
  public void testTriggeredStatechartsStore3(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "tf/Example.sc",
      "-s", outputDir + "testsc/Example.scsym"
    });
    assertEquals(Log.getErrorCount(), 0, "Storing symbol table of Example.sc was not successful");
  }


  @Test
  public void testTriggeredStatechartsPP3(){
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-pp", outputDir + "testsc/Car2.sc"
    });
    Log.getFindings().forEach(System.out::println);
    assertEquals(Log.getErrorCount(), 0, "Pretty printing Car2.sc was not successful");
  }


  @Test
  public void testTriggeredStatechartsReportDoor() throws IOException {
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Door3.sc",
      "-r", outputDir + "door3"
    });
    assertEquals(Log.getErrorCount(), 0, "Reporting for Door3.sc was not successful");
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "door3/branchingDegree.txt"));
    assertEquals(Integer.valueOf(1), branchingDegree.getOrDefault("Opened", -1), "Branching Degree of Opened");
    assertEquals(Integer.valueOf(2), branchingDegree.getOrDefault("Closed", -1), "Branching Degree of Closed");
    assertEquals(Integer.valueOf(1), branchingDegree.getOrDefault("Locked", -1), "Branching Degree of Locked");

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "door3/reachability.txt"));
    assertEquals("reachable", reachability.get("Closed"), "Reachability of Closed");
    assertEquals("reachable", reachability.get("Opened"), "Reachability of Opened");
    assertEquals("reachable", reachability.get("Locked"), "Reachability of Locked");

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "door3/stateNames.txt"));
    for (String state : Arrays.asList("Closed", "Opened", "Locked"))
      assertTrue(stateNames.contains(state), "StateNames " + state);
  }

  @Test
  public void testTriggeredStatechartsReportCar() throws IOException{
    new TriggeredStatechartsTool().run(new String[]{
      "-i", resourcesDir + "examples/triggered/Car2.sc",
      "-r", outputDir + "/car2"
    });
    assertEquals(Log.getErrorCount(), 0, "Reporting for Car.sc was not successful");
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "car2/branchingDegree.txt"));
    assertEquals(Integer.valueOf(1), branchingDegree.getOrDefault("EngineOff", -1), "Branching Degree of EngineOff");
    assertEquals(Integer.valueOf(1), branchingDegree.getOrDefault("EngineRunning", -1), "Branching Degree of EngineRunning");
    assertEquals(Integer.valueOf(0), branchingDegree.getOrDefault("Parking", -1), "Branching Degree of Parking");

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "car2/reachability.txt"));
    assertEquals("reachable", reachability.get("EngineOff"), "Reachability of EngineOff");
    assertEquals("reachable", reachability.get("EngineRunning"), "Reachability of EngineRunning");
    assertEquals("unreachable", reachability.get("Driving"), "Reachability of Driving");
    assertEquals("reachable", reachability.get("Parking"), "Reachability of Parking");

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "car2/stateNames.txt"));
    for (String state : Arrays.asList("EngineOff", "EngineRunning", "Driving", "Parking"))
      assertTrue(stateNames.contains(state), "StateNames " + state);

  }

  /**
   * Loads the generated branching degree report into a map
   * Each state name is mapped to its branching degree
   * @param file the path to the reports text file
   * @return a map with the branching degree report
   */
  private Map<String, Integer> loadBranchingDegree(File file)
    throws IOException {
    assertTrue(file.exists(), "branchingDegree report missing");
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
    assertTrue(file.exists(), "reachability report missing");
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
    assertTrue(file.exists(), "stateNames report missing");
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
    new TriggeredStatechartsTool().run(new String[]{    "-h" });
    assertEquals(Log.getErrorCount(), 0);
    String result = out.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
    assertTrue(result.contains("usage:  TriggeredStatechartsTool [-ct <file>] [-fp <pathlist>]"));
    assertTrue(result.contains("--help"));
    assertTrue(result.contains("-pp"));
  }

}
