/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scactions._ast.ASTSCAction;
import de.monticore.scdoactions._ast.ASTSCDoAction;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * This test parses every non terminal of SCDoActions,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCDoActionsParserPPTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter();
  UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCDoAction() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do / ");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCDoAction> astPP = parser.parse_StringSCDoAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testSCActionDo() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCAction> astPP = parser.parse_StringSCAction(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

}
