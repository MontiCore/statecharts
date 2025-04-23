/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scbasis._ast.*;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This test parses every non terminal of SCBasis,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCBasisParserPPTest extends GeneralAbstractTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsFullPrettyPrinter prettyPrinter = new UMLStatechartsFullPrettyPrinter(new IndentPrinter());

  @Test
  public void testSCArtifact() throws IOException {
    Optional<ASTSCArtifact> ast = parser.parse_StringSCArtifact("package a.b.c;" + "import de.monticore.cd;" + "statechart SC {" + "}");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertEquals("a.b.c", ast.get().getPackage().toString());

    assertEquals(1, ast.get().sizeMCImportStatements());
    assertEquals("de.monticore.cd", ast.get().getMCImportStatement(0).getQName());

    String pp = prettyPrinter.prettyprint(ast.get());
    Optional<ASTSCArtifact> astPP = parser.parse_StringSCArtifact(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testSCStatechart() throws IOException {
    Optional<ASTNamedStatechart> ast = parser.parse_StringNamedStatechart("statechart SC {" + "state S1;" + "}");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");
    assertEquals("SC", ast.get().getName());

    assertEquals(1, ast.get().getSCStatechartElementList().size());
    assertEquals("S1", ((ASTSCState) ast.get().getSCStatechartElementList().get(0)).getName());

    String pp = prettyPrinter.prettyprint(ast.get());
    Optional<ASTStatechart> astPP = parser.parse_StringStatechart(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testSCState() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial state S1;");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");

    assertTrue(ast.get().getSCModifier().isInitial());
    assertEquals("S1", ast.get().getName());

    String pp = prettyPrinter.prettyprint(ast.get());
    Optional<ASTSCState> astPP = parser.parse_StringSCState(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);
  }

  @Test
  public void testSCTransition() throws IOException {
    Optional<ASTSCTransition> ast = parser.parse_StringSCTransition("<<stereotype, stereo=\"mexico\">>S1 -> S2 ;");
    TestUtils.check(parser);
    assertTrue(ast.isPresent(), "No ast present");

    assertEquals(2, ast.get().getStereotype().getValuesList().size());
    assertEquals("stereotype", ast.get().getStereotype().getValues(0).getName());
    assertEquals("", ast.get().getStereotype().getValues(0).getValue());

    assertEquals("stereo", ast.get().getStereotype().getValues(1).getName());

    assertEquals("S1", ast.get().getSourceName());
    assertEquals("S2", ast.get().getTargetName());

    String pp = prettyPrinter.prettyprint(ast.get());
    Optional<ASTSCTransition> astPP = parser.parse_StringSCTransition(pp);
    assertTrue(astPP.isPresent(), "Failed to parse from pp: " + pp);
    assertTrue(astPP.get().deepEquals(ast.get()), "AST not equal after pp: " + pp);

    // Test this AFTER deepEquals, as de.monticore.umlstereotype._ast.ASTStereoValue#getValue() is not side effect free
    assertEquals("mexico", ast.get().getStereotype().getValues(1).getValue());
  }

}
