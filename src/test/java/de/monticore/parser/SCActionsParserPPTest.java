/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scactions._ast.ASTSCAction;
import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.scactions._ast.ASTSCExitAction;
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
 * This test parses every non terminal of SCActions,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCActionsParserPPTest extends GeneralAbstractTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());
  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testSCEntryAction() throws IOException {
    Optional<ASTSCEntryAction> ast = parser.parse_StringSCEntryAction("entry /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCEntryAction> astPP = parser.parse_StringSCEntryAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testSCExitAction() throws IOException {
    Optional<ASTSCExitAction> ast = parser.parse_StringSCExitAction("exit /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCExitAction> astPP = parser.parse_StringSCExitAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testSCActionEntry() throws IOException {
    Optional<ASTSCAction> ast = parser.parse_StringSCAction("entry /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCAction> astPP = parser.parse_StringSCAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testSCActionExit() throws IOException {
    Optional<ASTSCAction> ast = parser.parse_StringSCAction("exit /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCAction> astPP = parser.parse_StringSCAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

}
