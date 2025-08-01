/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test parses every non terminal of SCStateInvariants,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCStateInvariantsParserPPTest extends GeneralAbstractTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testSCStateInvariant() throws IOException {
    Optional<ASTSCInvState> ast = parser.parse_StringSCInvState("state Foo [ true && !false];");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");

    String pp = new UMLStatechartsFullPrettyPrinter(new IndentPrinter()).prettyprint(ast.get());
    Optional<ASTSCInvState> astPP = parser.parse_StringSCInvState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }
}
