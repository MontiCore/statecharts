/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test parses every non terminal of SCCompleteness,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCCompletenessParserPPTest extends GeneralAbstractTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());
  
  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testSCCompletenessComplete() throws IOException {
    Optional<ASTSCCompleteness> ast = parser.parse_StringSCCompleteness("(c)");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertTrue(ast.get().isComplete(), "Expected complete");
    assertFalse(ast.get().isIncomplete(), "Expected not incomplete");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCCompleteness> astPP = parser.parse_StringSCCompleteness(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testSCCompletenessIncomplete()
      throws IOException {
    Optional<ASTSCCompleteness> ast = parser.parse_StringSCCompleteness("(...)");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertFalse(ast.get().isComplete(), "Expected not complete");
    assertTrue(ast.get().isIncomplete(), "Expected incomplete");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCCompleteness> astPP = parser.parse_StringSCCompleteness(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

}
