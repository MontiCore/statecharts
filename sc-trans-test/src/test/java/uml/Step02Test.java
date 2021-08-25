package uml;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.monticore.tf.Step02ChangeInternTrans;
import de.monticore.tf.Step02RemoveDuplicatedSubstates;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class Step02Test extends AbstractUMLTest {

  @BeforeClass
  public static void disableFailQuick() {
    UMLStatechartsMill.init();
    Log.enableFailQuick(false);
  }

  @Test
  public void testStep02() throws IOException {
    UMLStatechartsParser parser = new UMLStatechartsParser();
    Optional<ASTSCArtifact> astSC = parser.parse("src/test/resources/Step02.sc");
    assertTrue(astSC.isPresent());

    //transform step one
    Step02ChangeInternTrans change = new Step02ChangeInternTrans(astSC.get());
    while (change.doPatternMatching()) {
      change.doReplacement();
      change = new Step02ChangeInternTrans(astSC.get());
    }

    // remove duplicated states
    Step02RemoveDuplicatedSubstates removeDupl = new Step02RemoveDuplicatedSubstates(astSC.get());
    while (removeDupl.doPatternMatching()) {
      removeDupl.doReplacement();
      removeDupl = new Step02RemoveDuplicatedSubstates(astSC.get());
    }

    controlAgainst("src/test/resources/control/Step02.sc", astSC.get());
  }

}
