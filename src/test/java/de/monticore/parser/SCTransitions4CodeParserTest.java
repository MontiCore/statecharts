/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This test parses every non terminal of SCTransitions4Code,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCTransitions4CodeParserTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testTransitionBodyPre() throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ]");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertTrue("Pre", ast.get().isPresentPre());
    assertFalse("action", ast.get().isPresentTransitionAction());

    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testTransitionBodyPreAndEvent()
      throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] a.b.c");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertTrue("Pre", ast.get().isPresentPre());
    assertTrue("event", ast.get().isPresentSCEvent());
    assertFalse("action", ast.get().isPresentTransitionAction());

    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testTransitionBody() throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] a.b.c / {doStuff(); }");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertTrue("Pre", ast.get().isPresentPre());
    assertTrue("event", ast.get().isPresentSCEvent());
    assertTrue("action", ast.get().isPresentTransitionAction());

    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

}
