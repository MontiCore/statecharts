/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scbasis._ast.*;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * This test parses every non terminal of SCBasis,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCBasisParserPPTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsPrettyPrinterDelegator prettyPrinter = new UMLStatechartsPrettyPrinterDelegator();

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

    checkPP(ast.get(), parser::parse_StringSCArtifact);
  }

  @Test
  public void testSCStatechart() throws IOException {
    Optional<ASTStatechart> ast = parser.parse_StringStatechart("statechart SC {" + "state S1;" + "}");
    check();
    assertTrue("No ast present", ast.isPresent());
    assertEquals("SC", ast.get().getName());

    assertEquals(1, ast.get().getSCStatechartElementsList().size());
    assertEquals("S1", ((ASTSCState) ast.get().getSCStatechartElementsList().get(0)).getName());

    checkPP(ast.get(), parser::parse_StringStatechart);
  }

  @Test
  public void testSCState() throws IOException {
    Optional<ASTSCState> ast = parser.parse_StringSCState("initial state S1;");
    check();
    assertTrue("No ast present", ast.isPresent());

    assertTrue(ast.get().getSCModifier().isInitial());
    assertEquals("S1", ast.get().getName());

    checkPP(ast.get(), parser::parse_StringSCState);
  }

  @Test
  public void testSCTransition() throws IOException {
    Optional<ASTSCTransition> ast = parser.parse_StringSCTransition("<<stereotype, stereo=\"mexico\">>S1 -> S2 ;");
    check();
    assertTrue("No ast present", ast.isPresent());

    assertEquals(2, ast.get().getStereotype().getValuesList().size());
    assertEquals("stereotype", ast.get().getStereotype().getValues(0).getName());
    assertNull(ast.get().getStereotype().getValues(0).getContent());

    assertEquals("stereo", ast.get().getStereotype().getValues(1).getName());

    assertEquals("S1", ast.get().getSourceName());
    assertEquals("S2", ast.get().getTargetName());



    checkPP(ast.get(), parser::parse_StringSCTransition);

    // Test this AFTER deepEquals, as de.monticore.umlstereotype._ast.ASTStereoValue#getValue() is not side effect free
    assertEquals("mexico", ast.get().getStereotype().getValues(1).getValue());
  }

  private void check() {
    LogStub.getFindings().forEach(System.err::println);
    assertFalse(parser.hasErrors());
  }

  /**
   * Test the pretty printer
   *
   * @param ast the ast node
   * @param fkt the parse function
   * @param <T> the ast nodes type
   * @throws IOException the parser has these declared
   */
  protected <T extends ASTSCBasisNode> void checkPP(T ast, @Nonnull CheckedFunction<String, Optional<T>> fkt)
      throws IOException {
    String pp = prettyPrinter.prettyprint(ast);
    Optional<T> astPP = fkt.apply(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast));
  }

  @FunctionalInterface
  public interface CheckedFunction<T, R> {
    R apply(T t) throws IOException;
  }

}
