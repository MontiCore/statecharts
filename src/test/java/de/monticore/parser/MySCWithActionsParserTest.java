/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class MySCWithActionsParserTest {
  
  UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @Before
  public void init(){
    Log.enableFailQuick(false);
  }
  
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
  public void testState2() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  entry / { ringTheDoorBell(); }"
        + "};");
    check(parser);
  }
  @Test
  public void testState3() throws IOException {
    parser.parse_StringSCState("state Opened {\n"
        + "  exit [!muted] / { ringTheDoorBell(); }\n"
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
  
  protected void check(UMLStatechartsParser parser) {
    if (parser.hasErrors()) {
      for(Finding f : LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}
