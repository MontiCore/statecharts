/* (c) https://github.com/MontiCore/monticore */

package uml;

import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsTool;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class TrafoWorkflowTest {
  @Rule
  public final TemporaryFolder temporaryFolder = new TemporaryFolder();

  private void initLogger() {
    LogStub.init();
    Log.enableFailQuick(false);
  }

  private void initMills() {
    UMLStatechartsMill.reset();
    UMLStatechartsMill.init();
  }

  @Before
  public void setup() {
    initLogger();
    initMills();
  }
  /**
   * This test tests the groovy transformation workflow of the tool
   * and compares the output.
   * This workflow adds two states, a transition between them, and a transition to the "B" state from every state
   */
  @Test
  public void testTrafoWorkflow() throws IOException {
    File ppFile = temporaryFolder.newFile();
    UMLStatechartsTool.main(new String[]{
            "-i", "src/test/resources/TestStatechart.sc",
            "-t", "src/test/resources/TrafoWorkflow.groovy",
            "-pp", ppFile.getAbsolutePath()
    });
    Assert.assertEquals("Errors during tool call", 0, Log.getErrorCount());
    Optional<ASTSCArtifact> astOpt = UMLStatechartsMill.parser().parse(new BufferedReader(new FileReader(ppFile)));

    Assert.assertEquals("Errors during parsing", 0, Log.getErrorCount());
    Assert.assertTrue("Failed to parse", astOpt.isPresent());

    StateCollector stateCollector = new StateCollector();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(stateCollector);
    astOpt.get().accept(traverser);
    Assert.assertEquals("Invalid count of states", 4, stateCollector.getStates().size());
    Assert.assertEquals("Invalid count of transitions", 4 + 3,
                        astOpt.get().getStatechart().getSCStatechartElementList().size());
  }
}
