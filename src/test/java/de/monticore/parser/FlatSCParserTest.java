/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.scbasis._ast.*;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * This test parses every non terminal,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class FlatSCParserTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCArtifact() throws IOException {
    Optional<ASTSCArtifact> ast = parser.parse_StringSCArtifact("package a.b.c;" + "import de.monticore.cd;" + "statechart SC {" + "}");
    check();
    assertTrue("No ast present", ast.isPresent());
    assertEquals("a.b.c", ast.get().getPackage().toString());

    assertEquals(1, ast.get().sizeMCImportStatements());
    assertEquals("de.monticore.cd", ast.get().getMCImportStatements(0).getQName());
  }

  @Test
  public void testSCStatechart() throws IOException {
    Optional<ASTStatechart> ast = parser.parse_StringStatechart("statechart SC {" + "state S1;" + "}");
    check();
    assertTrue("No ast present", ast.isPresent());
    assertEquals("SC", ast.get().getName());

    assertEquals(1, ast.get().getSCStatechartElementsList().size());
    assertEquals("S1", ((ASTSCState) ast.get().getSCStatechartElementsList().get(0)).getName());
  }

  @Test
  public void testSCState() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial state S1;");
    check();
    assertTrue("No ast present", ast.isPresent());

    assertTrue(ast.get().getSCModifier().isInitial());
    assertEquals("S1", ast.get().getName());
  }

  @Test
  public void testSCTransition() throws IOException {
    Optional<ASTSCTransition> ast = parser.parse_StringSCTransition("<<stereo>> S1 -> S2;");
    check();
    assertTrue("No ast present", ast.isPresent());

    assertEquals(1, ast.get().getStereotype().getValuesList().size());
    assertEquals("stereo", ast.get().getStereotype().getValues(0).getName());
    assertNull(ast.get().getStereotype().getValues(0).getContent());
    assertEquals("S1", ast.get().getSourceName());
    assertEquals("S2", ast.get().getTargetName());
  }

  private void check() {
    LogStub.getFindings().forEach(System.err::println);
    assertFalse(parser.hasErrors());
  }

}
