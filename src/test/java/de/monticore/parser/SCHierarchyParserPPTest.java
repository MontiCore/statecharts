/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.parser.util.TestUtils;
import de.monticore.prettyprint.UMLStatechartsPrettyPrinterDelegator;
import de.monticore.scstatehierarchy._ast.ASTSCHierarchyBody;
import de.monticore.scstatehierarchy._ast.ASTSCInternTransition;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * This test parses every non terminal of SCHierarchy,
 * checks it against expected values,
 * and validates that the PrettyPrinter returns an equivalent model
 */
public class SCHierarchyParserPPTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsPrettyPrinterDelegator prettyPrinter = new UMLStatechartsPrettyPrinterDelegator();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testSCHierarchyBody() throws IOException {
    Optional<ASTSCHierarchyBody> ast = parser.parse_StringSCHierarchyBody(" { entry / ; <<abc>> -> ;}");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());
    assertEquals(2, ast.get().getSCStateElementsList().size());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCHierarchyBody);
  }

  @Test
  public void testSCInternTransition() throws IOException {
    Optional<ASTSCInternTransition> ast = parser.parse_StringSCInternTransition("<<stereotype>> ->  ;");
    TestUtils.check(parser);
    assertTrue("No ast present", ast.isPresent());

    assertEquals(1, ast.get().getStereotype().getValuesList().size());
    assertEquals("stereotype", ast.get().getStereotype().getValues(0).getName());
    assertNull(ast.get().getStereotype().getValues(0).getContent());

    TestUtils.checkPP(ast.get(), parser::parse_StringSCInternTransition);
  }

}
