/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TriggeredStatechartsParserTest extends GeneralAbstractTest {
  
  TriggeredStatechartsParser parser = TriggeredStatechartsMill.parser();

  @Override
  @BeforeEach
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
