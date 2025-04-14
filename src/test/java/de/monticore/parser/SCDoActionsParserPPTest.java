/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scactions._ast.ASTSCAction;
import de.monticore.scdoactions._ast.ASTSCDoAction;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test parses every non terminal of SCDoActions,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCDoActionsParserPPTest extends GeneralAbstractTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());
  UMLStatechartsParser parser = new UMLStatechartsParser();


  @Test
  public void testSCDoAction() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do / ");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCDoAction> astPP = parser.parse_StringSCDoAction(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testSCActionDo() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do /");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCAction> astPP = parser.parse_StringSCAction(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

}
