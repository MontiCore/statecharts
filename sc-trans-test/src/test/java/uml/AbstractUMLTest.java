package uml;

import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import org.junit.ComparisonFailure;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public abstract class AbstractUMLTest {
  protected void controlAgainst(String filename, ASTSCArtifact astscArtifact) throws IOException {
    UMLStatechartsParser parser = new UMLStatechartsParser();
    Optional<ASTSCArtifact> astSC = parser.parse(filename);
    assertTrue(astSC.isPresent());

    UMLStatechartsFullPrettyPrinter fpp = new UMLStatechartsFullPrettyPrinter();

    if (!astscArtifact.deepEquals(astSC.get())){
      throw new ComparisonFailure("Control SC did not match",fpp.prettyprint(astSC.get()),
          fpp.prettyprint(astscArtifact));
    }
  }
}
