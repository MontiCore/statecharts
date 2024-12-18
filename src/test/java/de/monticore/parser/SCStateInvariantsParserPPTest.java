/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
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
public class SCStateInvariantsParserPPTest extends GeneralAbstractTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testSCStateInvariant() throws IOException {
    Optional<ASTSCInvState> ast = parser.parse_StringSCInvState("state Foo [ true && !false];");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = new UMLStatechartsFullPrettyPrinter(new IndentPrinter()).prettyprint(ast.get());
    Optional<ASTSCInvState> astPP = parser.parse_StringSCInvState(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testSCStateInvariantWithAnte() throws IOException {
    Optional<ASTSCInvState> ast = parser.parse_StringSCInvState("initial { System.out.println(\"Ante allowed here\"); } state Foo [ true && !false];");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = new UMLStatechartsFullPrettyPrinter(new IndentPrinter()).prettyprint(ast.get());
    Optional<ASTSCInvState> astPP = parser.parse_StringSCInvState(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }
}
