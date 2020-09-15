/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser.util;

import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.LogStub;

import static org.junit.Assert.assertFalse;

public class TestUtils {
  private TestUtils(){}


  public  static void check(UMLStatechartsParser parser) {
    LogStub.getFindings().forEach(System.err::println);
    assertFalse(parser.hasErrors());
  }

}
