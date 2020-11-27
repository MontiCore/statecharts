/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UMLStatechartsToolTest {
  
  String resourcesDir = "src/test/resources/";
  String outputDir = "target/tooltest/";
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.initPlusLog();
    UMLStatechartsMill.init();
  }
  
  @Before
  public void setUp() throws Exception {
    Log.clearFindings();
  }
  
  @Test
  public void testUMLStatecharts(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Door.sc"
    });
    assertEquals("Door.sc was not processed successfully", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Door.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Door.sc",
        "-st", outputDir + "door"
    });
    assertEquals("Storing symbol table of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore2(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Car.sc",
        "-st", outputDir + "car"
    });
    assertEquals("Storing symbol table of Car.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore3(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-st", outputDir + "testsc"
    });
    assertEquals("Storing symbol table of Test.sc was not successful", Log.getErrorCount(), 0);
  }
  @Test
  public void testUMLStatechartsPP3(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-pp", outputDir + "testsc/Test.sc"
    });
    assertEquals("Pretty printing Test.sc was not successful", Log.getErrorCount(), 0);
  }
  
  
  @Test
  public void testUMLStatechartsReport(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Door.sc",
        "-r", outputDir + "door"
    });
    assertEquals("Reporting for Door.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File(outputDir + "door/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File(outputDir + "door/reachability.txt").exists());
    assertTrue("stateNames report missing",new File(outputDir + "door/stateNames.txt").exists());
  }
  
  @Test
  public void testUMLStatechartsReportCar(){
    new StatechartsCLI().run(new String[]{
        "-i", resourcesDir + "examples/Car.sc",
        "-r", outputDir + "/car"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File(outputDir + "car/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File(outputDir + "car/reachability.txt").exists());
    assertTrue("stateNames report missing",new File(outputDir + "car/stateNames.txt").exists());
  }
  
}