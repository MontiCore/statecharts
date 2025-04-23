/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scbasis._ast.ASTSCEmptyAnte;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTUnnamedStatechart;
import de.monticore.sctransitions4code._ast.ASTAnteAction;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.statements.mccommonstatements._ast.ASTExpressionStatement;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test parses every non terminal of SCTransitions4Code,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCTransitions4CodeParserTest extends GeneralAbstractTest {
  
  UMLStatechartsFullPrettyPrinter printer = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());
  UMLStatechartsParser parser = new UMLStatechartsParser();


  @Test
  public void testTransitionBodyPre() throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ]");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertTrue(ast.get().isPresentPre(), "Pre");
    assertFalse(ast.get().isPresentTransitionAction(), "action");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testTransitionBodyPreAndEvent()
      throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] a.b.c");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertTrue(ast.get().isPresentPre(), "Pre");
    assertTrue(ast.get().isPresentSCEvent(), "event");
    assertFalse(ast.get().isPresentTransitionAction(), "action");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testTransitionBody() throws IOException {
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] a.b.c / {doStuff(); }");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertTrue(ast.get().isPresentPre(), "Pre");
    assertTrue(ast.get().isPresentSCEvent(), "event");
    assertTrue(ast.get().isPresentTransitionAction(), "action");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTTransitionBody> astPP = parser.parse_StringTransitionBody(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testAbsentAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("state A;");
    TestUtils.check(parser);

    assertTrue(ast.isPresent(), "No ast present");
    assertFalse(ast.get().getSCModifier().isPresentStereotype()
      || ast.get().getSCModifier().isFinal()
      || ast.get().getSCModifier().isInitial(),
        "Modifier");
    assertInstanceOf(ASTSCEmptyAnte.class, ast.get().getSCSAnte(), "Ante");
    assertEquals("A", ast.get().getName(), "State name");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testEmptyAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial {} state A;");
    TestUtils.check(parser);

    assertTrue(ast.isPresent(), "No ast present");
    assertFalse(ast.get().getSCModifier().isPresentStereotype()
        || ast.get().getSCModifier().isFinal(),
        "Modifier");
    assertTrue(ast.get().getSCModifier().isInitial(), "Initial");
    assertInstanceOf(ASTAnteAction.class, ast.get().getSCSAnte(), "Ante");
    assertEquals(0, ((ASTAnteAction) ast.get().getSCSAnte()).sizeMCBlockStatements(), "Statement count");
    assertEquals("A", ast.get().getName(), "State name");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial { \"foo\"; } state Foo;");
    TestUtils.check(parser);

    assertTrue(ast.isPresent(), "No ast present");
    assertFalse(ast.get().getSCModifier().isPresentStereotype(), "Stereotype");
    assertFalse(ast.get().getSCModifier().isFinal(), "final");
    assertTrue(ast.get().getSCModifier().isInitial(), "initial");
    assertInstanceOf(ASTAnteAction.class, ast.get().getSCSAnte(), "Ante");
    assertEquals(1, ((ASTAnteAction) ast.get().getSCSAnte()).sizeMCBlockStatements(), "Statement count");
    assertInstanceOf(ASTExpressionStatement.class,
        ((ASTAnteAction) ast.get().getSCSAnte()).getMCBlockStatement(0), "Expression");
    assertEquals("Foo", ast.get().getName(), "State name");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testBigAnteBlock() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial { \"foo\"; \"bar\"; } state Foo;");
    TestUtils.check(parser);

    assertTrue(ast.isPresent(), "No ast present");
    assertFalse(ast.get().getSCModifier().isPresentStereotype(), "Stereotype");
    assertFalse(ast.get().getSCModifier().isFinal(), "final");
    assertTrue(ast.get().getSCModifier().isInitial(), "initial");
    assertInstanceOf(ASTAnteAction.class, ast.get().getSCSAnte(), "Ante");
    assertEquals(2, ((ASTAnteAction) ast.get().getSCSAnte()).sizeMCBlockStatements(), "Statement count");
    assertInstanceOf(ASTExpressionStatement.class,
        ((ASTAnteAction) ast.get().getSCSAnte()).getMCBlockStatement(0), "Expression #1");
    assertInstanceOf(ASTExpressionStatement.class,
        ((ASTAnteAction) ast.get().getSCSAnte()).getMCBlockStatement(1), "Expression #2");
    assertEquals("Foo", ast.get().getName(), "State name");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testMultipleStatesWithoutAnte() throws IOException {
    Optional<ASTUnnamedStatechart> ast =
      parser.parse_StringUnnamedStatechart("statechart { initial state Foo; state Bar; }");
    TestUtils.check(parser);

    assertTrue(ast.isPresent(), "No ast present");
    assertEquals(2, ast.get().sizeSCStatechartElements(), "Element count");
    ASTSCState firstState = ((ASTSCState) ast.get().getSCStatechartElement(0));
    ASTSCState secondState = ((ASTSCState) ast.get().getSCStatechartElement(1));

    assertTrue(firstState.getSCModifier().isInitial(), "initial State");
    assertEquals("Foo", firstState.getName(), "initial State name");
    assertInstanceOf(ASTSCEmptyAnte.class, firstState.getSCSAnte(), "initial state ante");

    assertFalse(secondState.getSCModifier().isInitial(), "second State");
    assertEquals("Bar", secondState.getName(), "second State name");
    assertInstanceOf(ASTSCEmptyAnte.class, secondState.getSCSAnte(), "second state ante");

    String pp = printer.prettyprint(ast.get());
    Optional<ASTUnnamedStatechart> astPP = parser.parse_StringUnnamedStatechart(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }
}
