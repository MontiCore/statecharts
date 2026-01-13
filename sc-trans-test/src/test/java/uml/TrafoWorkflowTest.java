/* (c) https://github.com/MontiCore/monticore */

package uml;

import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsTool;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TrafoWorkflowTest {
  @TempDir
  public Path temporaryFolder;

  private void initLogger() {
    LogStub.init();
    Log.enableFailQuick(false);
  }

  private void initMills() {
    UMLStatechartsMill.reset();
    UMLStatechartsMill.init();
  }

  @BeforeEach
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
    File ppFile = temporaryFolder.resolve("trafoOut.sc").toFile();
    assertTrue(ppFile.createNewFile());
    new UMLStatechartsTool().run(new String[]{
            "-i", "src/test/resources/TestStatechart.sc",
            "-t", "src/test/resources/TrafoWorkflow.groovy",
            "-pp", ppFile.getAbsolutePath()
    });
    assertEquals(0, Log.getErrorCount(), "Errors during tool call");
    Optional<ASTSCArtifact> astOpt =
        UMLStatechartsMill.parser().parse(new BufferedReader(new FileReader(ppFile)));
    
    assertEquals(0, Log.getErrorCount(), "Errors during parsing");
    assertTrue(astOpt.isPresent(), "Failed to parse");
    
    StateCollector stateCollector = new StateCollector();
    UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
    traverser.add4SCBasis(stateCollector);
    astOpt.get().accept(traverser);
    assertEquals(4, stateCollector.getStates().size(), "Invalid count of states");
    assertEquals(4 + 3, astOpt.get().getStatechart().getSCStatechartElementList().size(),
        "Invalid count of transitions");
  }
}
