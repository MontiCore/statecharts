/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
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

  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsPrettyPrinterDelegator prettyPrinter = new UMLStatechartsPrettyPrinterDelegator();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCDoAction() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do / ");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCDoAction);
  }


  @Test
  public void testSCActionDo() throws IOException {
    Optional<ASTSCDoAction> ast = parser.parse_StringSCDoAction("do /");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCAction);
  }

}
