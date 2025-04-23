/* (c) https://github.com/MontiCore/monticore */
package uml;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.tf.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Step09Test extends AbstractUMLTest {

  @BeforeEach
  public void setup() {
    super.initLogger();
    super.initMills();
  }

  @Test
  public void testStep09() throws IOException {
    UMLStatechartsParser parser = new UMLStatechartsParser();
    Optional<ASTSCArtifact> astSC = parser.parse("src/test/resources/Step09.sc");
    assertTrue(astSC.isPresent());

    Step09MoveHierarchicalStates moveHierarchicalStates = new Step09MoveHierarchicalStates(astSC.get());
    while (moveHierarchicalStates.doPatternMatching()) {
      moveHierarchicalStates.doReplacement();
      moveHierarchicalStates = new Step09MoveHierarchicalStates(astSC.get());
    }

    controlAgainst("src/test/resources/control/Step09.sc", astSC.get());
  }

}
