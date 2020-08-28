/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scstateinvariants._ast.ASTSCStateInvariant;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * This test parses every non terminal of SCStateInvariants,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCStateInvariantsParserPPTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCStateInvariant() throws IOException {
    Optional<ASTSCStateInvariant> ast = parser.parse_StringSCStateInvariant(" [ true && !false]");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast.get());
    Optional<ASTSCStateInvariant> astPP = parser.parse_StringSCStateInvariant(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

}
