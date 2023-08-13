/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser.util;

import de.monticore.GeneralAbstractTest;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;

import static org.junit.Assert.assertFalse;

public class TestUtils extends GeneralAbstractTest {

  private TestUtils(){}


  public  static void check(UMLStatechartsParser parser) {
    LogStub.getFindings().forEach(System.err::println);
    assertFalse(parser.hasErrors());
  }

}
