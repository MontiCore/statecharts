/* (c) https://github.com/MontiCore/monticore */
package uml;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;


import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractUMLTest {

  protected void initLogger() {
    LogStub.init();
    Log.enableFailQuick(false);
  }

  protected void initMills() {
    UMLStatechartsMill.reset();
    UMLStatechartsMill.init();
  }
  protected void controlAgainst(String filename, ASTSCArtifact astscArtifact) throws IOException {
    UMLStatechartsParser parser = new UMLStatechartsParser();
    Optional<ASTSCArtifact> astSC = parser.parse(filename);
    assertTrue(astSC.isPresent());

    UMLStatechartsFullPrettyPrinter fpp = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());

    if (!astscArtifact.deepEquals(astSC.get())){
      assertEquals(fpp.prettyprint(astSC.get()), fpp.prettyprint(astscArtifact),
          "Control SC did not match");
    }
  }
}
