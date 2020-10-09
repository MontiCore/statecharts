/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
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
  }
  

  @Test
  public void testUMLStatecharts(){
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "examples/Door.sc"
    });
    assertEquals("Door.sc was not processed successfully", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP(){
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "examples/Door.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore(){
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "examples/Door.sc",
        "-st", outputDir + "door"
    });
    assertEquals("Storing symbol table of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore2(){
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "examples/Car.sc",
        "-st", outputDir + "car"
    });
    assertEquals("Storing symbol table of Car.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsStore3(){
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "valid/Test.sc",
        "-st", outputDir + "test"
    });
    assertEquals("Storing symbol table of Test.sc was not successful", Log.getErrorCount(), 0);
  }
  
  
  @Test
  public void testUMLStatechartsReport(){
    StatechartsCLI.main(new String[]{
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
    StatechartsCLI.main(new String[]{
        "-i", resourcesDir + "examples/Car.sc",
        "-r", outputDir + "/car"
    });
    assertEquals("Reporting for Car.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File(outputDir + "car/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File(outputDir + "car/reachability.txt").exists());
    assertTrue("stateNames report missing",new File(outputDir + "car/stateNames.txt").exists());
  }
  
}