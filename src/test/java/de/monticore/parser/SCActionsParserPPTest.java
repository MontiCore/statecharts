/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.scactions._ast.ASTSCAction;
import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.scactions._ast.ASTSCExitAction;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
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
public class SCActionsParserPPTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCEntryAction() throws IOException {
    Optional<ASTSCEntryAction> ast = parser.parse_StringSCEntryAction("entry /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCEntryAction);
  }

  @Test
  public void testSCExitAction() throws IOException {
    Optional<ASTSCExitAction> ast = parser.parse_StringSCExitAction("exit /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCExitAction);
  }

  @Test
  public void testSCActionEntry() throws IOException {
    Optional<ASTSCAction> ast = parser.parse_StringSCAction("entry /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCAction);
  }

  @Test
  public void testSCActionExit() throws IOException {
    Optional<ASTSCAction> ast = parser.parse_StringSCAction("exit /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCAction);
  }


}
