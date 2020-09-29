/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.io.paths.ModelPath;
import de.monticore.umlstatecharts._visitor.UMLStatechartsDelegatorVisitor;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UMLStatechartsToolTest {
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.initPlusLog();
  }
  

  @Test
  public void testUMLStatecharts(){
    StatechartsCLI.main(new String[]{
        "-i", "src/test/resources/examples/Door.sc"
    });
    assertEquals("Door.sc was not processed successfully", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsPP(){
    StatechartsCLI.main(new String[]{
        "-i", "src/test/resources/examples/Door.sc",
        "-pp"
    });
    assertEquals("Pretty printing of Door.sc was not successful", Log.getErrorCount(), 0);
  }
  
  @Test
  public void testUMLStatechartsReport(){
    StatechartsCLI.main(new String[]{
        "-i", "src/test/resources/examples/Door.sc",
        "-r", "target"
    });
    assertEquals("Reporting for Door.sc was not successful", Log.getErrorCount(), 0);
    assertTrue("branchingDegree report missing", new File("target/branchingDegree.txt").exists());
    assertTrue("reachability report missing",new File("target/reachability.txt").exists());
    assertTrue("stateNames report missing",new File("target/stateNames.txt").exists());
  }
  
}