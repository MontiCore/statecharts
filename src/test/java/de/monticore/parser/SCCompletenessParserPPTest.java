/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This test parses every non terminal of SCCompleteness,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCCompletenessParserPPTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCCompletenessComplete() throws IOException {
    Optional<ASTSCCompleteness> ast = parser.parse_StringSCCompleteness("(c)");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertTrue("Expected complete", ast.get().isComplete());
    assertFalse("Expected not incomplete", ast.get().isIncomplete());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCCompleteness);
  }

  @Test
  public void testSCCompletenessIncomplete()
      throws IOException {
    Optional<ASTSCCompleteness> ast = parser.parse_StringSCCompleteness("(...)");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertFalse("Expected not complete", ast.get().isComplete());
    assertTrue("Expected incomplete", ast.get().isIncomplete());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCCompleteness);
  }


}
