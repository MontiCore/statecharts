/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.expressions.expressionsbasis._ast.ASTLiteralExpression;
import de.monticore.expressions.expressionsbasis._ast.ASTNameExpression;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCEmptyAnte;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTUnnamedStatechart;
import de.monticore.sctransitions4code._ast.ASTAnteAction;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.statements.mccommonstatements._ast.ASTExpressionStatement;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * This test parses every non terminal of SCTransitions4Code,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCTransitions4CodeParserTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter();
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

    String pp = printer.prettyprint(ast.get());
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

    String pp = printer.prettyprint(ast.get());
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

    String pp = printer.prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testEmptyAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("state A;");
    TestUtils.check(parser);

    assertTrue("No ast present", ast.isPresent());
    assertFalse("Modifier",
      ast.get().getSCModifier().isPresentStereotype()
      || ast.get().getSCModifier().isFinal()
      || ast.get().getSCModifier().isInitial());
    assertTrue("Ante", ast.get().getSCSAnte() instanceof ASTSCEmptyAnte);
    assertEquals("State name", "A", ast.get().getName());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial { \"foo\"; } state Foo;");
    TestUtils.check(parser);

    assertTrue("No ast present", ast.isPresent());
    assertFalse("Stereotype", ast.get().getSCModifier().isPresentStereotype());
    assertFalse("final", ast.get().getSCModifier().isFinal());
    assertTrue("initial", ast.get().getSCModifier().isInitial());
    assertTrue("Ante", ast.get().getSCSAnte() instanceof ASTAnteAction);
    assertTrue("Expression",
      ((ASTAnteAction) ast.get().getSCSAnte()).getMCBlockStatement() instanceof ASTExpressionStatement);
    assertEquals("State name", "Foo", ast.get().getName());

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }

  @Test
  public void testMultipleStatesWithoutAnte() throws IOException {
    Optional<ASTUnnamedStatechart> ast =
      parser.parse_StringUnnamedStatechart("statechart { initial state Foo; state Bar; }");
    TestUtils.check(parser);

    assertTrue("No ast present", ast.isPresent());
    assertEquals("Element count", 2, ast.get().sizeSCStatechartElements());
    ASTSCState firstState = ((ASTSCState) ast.get().getSCStatechartElement(0));
    ASTSCState secondState = ((ASTSCState) ast.get().getSCStatechartElement(1));

    assertTrue("initial State",firstState.getSCModifier().isInitial());
    assertEquals("initial State name", "Foo", firstState.getName());
    assertTrue("initial state ante", firstState.getSCSAnte() instanceof ASTSCEmptyAnte);

    assertFalse("second State",secondState.getSCModifier().isInitial());
    assertEquals("second State name", "Bar", secondState.getName());
    assertTrue("second state ante", secondState.getSCSAnte() instanceof ASTSCEmptyAnte);

    String pp = printer.prettyprint(ast.get());
    Optional<ASTUnnamedStatechart> astPP = parser.parse_StringUnnamedStatechart(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast.get()));
  }
}
