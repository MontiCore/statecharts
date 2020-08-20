/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scactions._ast.ASTSCABody;
import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4modelling._ast.ASTEventTransitionAction;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

/**
 * This test parses every non terminal of SCTransitions4Modelling,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCTransitions4ModellingParserTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsPrettyPrinterDelegator prettyPrinter = new UMLStatechartsPrettyPrinterDelegator();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testEventTransitionAction() throws IOException {
    Optional<ASTEventTransitionAction> ast = parser.parse_StringEventTransitionAction("{doStuff(); } [ false ]");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringEventTransitionAction);
  }


  @Test
  public void testSCABody() throws IOException {
    Optional<ASTSCABody> ast = parser.parse_StringSCABody("{doStuff(); } [ false ]");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
  }

  @Test
  public void testEventTransitionActionSCEntryAction() throws IOException {
    Optional<ASTSCEntryAction> ast = parser.parse_StringSCEntryAction(" entry / {doStuff(); } [ false ]");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCEntryAction);
  }

}
