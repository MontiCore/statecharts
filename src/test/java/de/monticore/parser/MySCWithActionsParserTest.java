/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.GeneralAbstractTest;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class MySCWithActionsParserTest extends GeneralAbstractTest {
  
  UMLStatechartsParser parser = new UMLStatechartsParser();

  
  @Test
  public void testStatechart() throws IOException {
    parser.parse_StringStatechart("statechart Foo {"
        + "  state Bla {"
        + "    state S;"
        + "  };"
        + "}");
    check(parser);
  }
  @Test
  public void testStatechart2() throws IOException {
    parser.parse_StringStatechart("statechart Door {"
        + "  state Opened; "
        + "}");
    check(parser);
  }
  
  @Test
  public void testTransition() throws IOException {
    parser.parse_StringSCTransition(" Closed -> Opened open() / {ringTheDoorBell();};");
    check(parser);
  }
  
  @Test
  public void testTransition2() throws IOException {
    parser.parse_StringSCTransition(" Closed -> Opened open() / { String foo = b;};");
    check(parser);
  }
  
  
  @Test
  public void testState2() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  entry / { ringTheDoorBell(); }"
        + "};");
    check(parser);
  }
  @Test
  public void testState3() throws IOException {
    parser.parse_StringSCState("state Opened {\n"
        + "  exit / { ringTheDoorBell(); } [!open]\n"
        + "};\n");
    check(parser);
  }
  
  @Test
  public void testState4() throws IOException {
    parser.parse_StringSCState("state Closed {"
        + "  -> timeOut() / { lockDoor(); };"
        + "};");
    check(parser);
  }
  
  @Test
  public void testState5() throws IOException {
    parser.parse_StringSCState("state Closed {"
        + "  -> timeOut() / { lockDoor(); String foo = b;};"
        + "};");
    check(parser);
  }
  
  @Test
  public void testState6() throws IOException {
    parser.parse_StringSCState("state Closed {"
        + " exit / {lightsOff(); String foo = b;}"
        + "};");
    check(parser);
  }
  protected void check(UMLStatechartsParser parser) {
    if (parser.hasErrors()) {
      for(Finding f : LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}
