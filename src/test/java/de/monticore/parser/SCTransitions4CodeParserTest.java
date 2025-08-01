/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
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
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] ;");
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
    Optional<ASTTransitionBody> ast = parser.parse_StringTransitionBody(" [ true ] a.b.c ;");
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
}
