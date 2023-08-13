/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.umlstatecharts.UMLStatechartsTool;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsGlobalScope;
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

public class UMLStatechartsToolTest extends GeneralAbstractTest{
  
  String resourcesDir = "src/test/resources/";
  String outputDir = "target/tooltest/";


  @After
  public void after(){
    CD4CodeMill.reset();
  }

  @Override
  @Before
  public void setUp() {
    initLogger();
    initUMLStatechartsMill();
    IUMLStatechartsGlobalScope gs = UMLStatechartsMill.globalScope();
    gs.clear();
    TypeSymbol stringType = UMLStatechartsMill
        .typeSymbolBuilder()
        .setEnclosingScope(gs)
        .setName("String")
        .build();
    UMLStatechartsMill.globalScope().add(stringType);
    IUMLStatechartsArtifactScope as = UMLStatechartsMill.artifactScope();
    as.setEnclosingScope(gs);
    as.setPackageName("a.b");
    TypeSymbol personType = UMLStatechartsMill
        .typeSymbolBuilder()
        .setEnclosingScope(as)
        .setName("Person")
        .build();
    as.add(personType);
  }

  // ------------------------------------------------------

  @Test
  public void testUMLStatecharts(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc"
    });
    assertEquals("Door.sc was not processed successfully", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP2(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "tf/Example.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Example.sc was not successful", Log.getErrorCount(), 0);
  }

  @Test
  public void testUMLStatechartsConverter(){
    new UMLStatechartsTool().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-gen", "target/gentest1"
    });
    assertEquals("Converting to SD of Door.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testUMLStatechartsConverterVariant2(){
    new UMLStatechartsTool().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-var", "StatePattern2",
            "-gen", "target/gentest1MitV2"
    });
    assertEquals("Converting to SD of DoorExample.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  // Testing Version 3: much is configured in a StatePatternConfigV3 template
  @Test
  public void testUMLStatechartsConverterVariant3(){
    new UMLStatechartsTool().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-gen", "target/gentest1MitV3",
            "-var", "StatePattern2",
            "-fp", "src/test/resources",
            "-ct", "configTemplate/StatePatternConfigV3.ftl"
    });
    assertEquals("Converting to CD of DoorExample.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testUMLStatechartsConverterWithConfigTemplate(){
    new UMLStatechartsTool().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-gen", "target/gentest2",
            "-fp", "src/test/resources",
            "-ct", "configTemplate/ct.ftl"
    });
    assertEquals("Converting to CD of Door.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testUMLStatechartsConverterWithConfigTemplateAndTOP(){
    new UMLStatechartsTool().run(new String[]{
            "-i", resourcesDir + "examples/uml/DoorExample.sc",
            "-gen", "target/gentest3",
            "-fp", "src/test/resources",
            "-ct", "configTemplate/ct.ftl",
            "-hcp", "src/test/resources/handcoded"
    });
    assertEquals("Converting to CD of Door.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }

  @Test
  public void testUMLStatechartsStore(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-s", outputDir + "door/Door.scsym"
    });
    assertEquals("Storing symbol table of Door.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }
  
  @Test
  public void testUMLStatechartsStore2(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Car.sc",
        "-s", outputDir + "car/Car.scsym"
    });
    assertEquals("Storing symbol table of Car.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }
  
  @Test
  public void testUMLStatechartsStore3(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-s", outputDir + "testsc/Test.scsym"
    });
    assertEquals("Storing symbol table of Test.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }

  @Test
  public void testUMLStatechartsStore4(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "valid/Test2.sc",
        "-s", outputDir + "testsc2/Test2.scsym"
    });
    assertEquals("Storing symbol table of Test2.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }


  @Test
  public void testUMLStatechartsPP3(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-pp", outputDir + "testsc/Test.sc"
    });
    Log.getFindings().forEach(System.out::println);
    assertEquals("Pretty printing Test.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }
  
  @Test
  public void testUMLStatechartsEvent(){
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "flat/test6.sc"
    });
    Log.getFindings().forEach(System.out::println);
    assertEquals("Processing of test6.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files is to be checked manually at the moment
  }
  
  
  @Test
  public void testUMLStatechartsReportDoor() throws IOException {
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Door.sc",
        "-r", outputDir + "door"
    });
    assertEquals("Reporting for Door.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "door/branchingDegree.txt"));
    assertEquals("Branching Degree of Opened", Integer.valueOf(1), branchingDegree.getOrDefault("Opened", -1));
    assertEquals("Branching Degree of Closed", Integer.valueOf(2), branchingDegree.getOrDefault("Closed", -1));
    assertEquals("Branching Degree of Locked", Integer.valueOf(1), branchingDegree.getOrDefault("Locked", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "door/reachability.txt"));
    assertEquals("Reachability of Closed", "reachable", reachability.get("Closed"));
    assertEquals("Reachability of Opened", "reachable", reachability.get("Opened"));
    assertEquals("Reachability of Locked", "reachable", reachability.get("Locked"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "door/stateNames.txt"));
    for (String state : Arrays.asList("Closed", "Opened", "Locked"))
      assertTrue("StateNames " + state, stateNames.contains(state));
  }
  
  @Test
  public void testUMLStatechartsReportCar() throws IOException{
    new UMLStatechartsTool().run(new String[]{
        "-i", resourcesDir + "examples/uml/Car.sc",
        "-r", outputDir + "/car"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    // Test the branching degree
    Map<String, Integer> branchingDegree = loadBranchingDegree(new File(outputDir + "car/branchingDegree.txt"));
    assertEquals("Branching Degree of EngineOff", Integer.valueOf(1), branchingDegree.getOrDefault("EngineOff", -1));
    assertEquals("Branching Degree of EngineRunning", Integer.valueOf(1), branchingDegree.getOrDefault("EngineRunning", -1));
    assertEquals("Branching Degree of Parking", Integer.valueOf(0), branchingDegree.getOrDefault("Parking", -1));

    // Test reachability reports
    Map<String, String> reachability = loadReachability(new File(outputDir + "car/reachability.txt"));
    assertEquals("Reachability of EngineOff", "reachable", reachability.get("EngineOff"));
    assertEquals("Reachability of EngineRunning", "reachable", reachability.get("EngineRunning"));
    assertEquals("Reachability of Driving", "unreachable", reachability.get("Driving"));
    assertEquals("Reachability of Parking", "reachable", reachability.get("Parking"));

    // Test state name report
    Set<String> stateNames= loadStateNames(new File(outputDir + "car/stateNames.txt"));
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
    new UMLStatechartsTool().run(new String[]{    "-h" });
    assertEquals(Log.getErrorCount(), 0);
    String result = out.toString().replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
    assertTrue( result.contains("usage: UMLStatechartsTool\n" +
                          " -ct,--configTemplate <file>       Provides a config template (optional)\n" +
                          " -fp,--templatePath <pathlist>     List of directories to look for handwritten\n" +
                          "                                   templates to integrate (optional)\n"
              ));
  }

  @Test
  public void testPingPong1A() {
    new UMLStatechartsTool().run(new String[]{
      "-i", resourcesDir + "de/mine/PingPong.sc",
      "-gen", "target/gentestPing1A"
    });
    assertEquals("Converting of PingPong.sc was not successful", Log.getErrorCount(), 0);
    // the content of the generated files will be checked later by Gradle, 
    // by compilation and execution
  }
}