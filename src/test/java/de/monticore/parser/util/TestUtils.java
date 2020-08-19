/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser.util;

import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.sctransitions4modelling._ast.ASTSCTransitions4ModellingNode;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.LogStub;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestUtils {
  private TestUtils(){}


  public  static void check(UMLStatechartsParser parser) {
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
  public  static  <T extends ASTSCBasisNode> void checkPP(T ast, @Nonnull CheckedFunction<String, Optional<T>> fkt)
      throws IOException {
    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast);
    Optional<T> astPP = fkt.apply(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast));
  }

  /**
   * Test the pretty printer
   *
   * @param ast the ast node
   * @param fkt the parse function
   * @param <T> the ast nodes type
   * @throws IOException the parser has these declared
   */
  public  static  <T extends ASTSCTransitions4ModellingNode> void checkPP(T ast, @Nonnull CheckedFunction<String, Optional<T>> fkt)
      throws IOException {
    String pp = new UMLStatechartsPrettyPrinterDelegator().prettyprint(ast);
    Optional<T> astPP = fkt.apply(pp);
    assertTrue("Failed to parse from pp: " + pp, astPP.isPresent());
    assertTrue("AST not equal after pp: " + pp, astPP.get().deepEquals(ast));
  }

  @FunctionalInterface
  public interface CheckedFunction<T, R> {
    R apply(T t) throws IOException;
  }
}
