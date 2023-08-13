/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class TriggeredStatechartsParserTest extends GeneralAbstractTest {
  
  TriggeredStatechartsParser parser = TriggeredStatechartsMill.parser();

  @Override
  @Before
  public void setUp() {
    initLogger();
    initTriggeredStatechartsMill();
  }

  @Test
  public void testTeaser() throws IOException {
    parser.parse("src/test/resources/examples/triggered/Door3.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTeaser2() throws IOException {
    parser.parse("src/test/resources/examples/triggered/Car2.sc");
    assertFalse(parser.hasErrors());
  }
  
}
