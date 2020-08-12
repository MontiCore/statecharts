/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class SCDemoParseTest {
  
  UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @BeforeClass
  public static void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void testTeaser() throws IOException {
    parser.parse("src/test/resources/examples/Door.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart() throws IOException {
    parser.parse_StringStatechart("statechart {"
        + "  // …\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart2() throws IOException {
    parser.parse_StringStatechart("statechart Door {"
        + "  // …\n"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testStatechart3() throws IOException {
    parser.parse_StringStatechart("statechart Door {  "
        + "  initial state Closed;"
        + "  state Opened {"
        + "    do { ringTheDoorBell(); }"
        + "  };"
        + "  state Locked;"
        + ""
        + "  Opened -> Closed close()  ;"
        + "  Closed -> Opened open()  / {ringTheDoorBell();};"
        + "  Closed -> Locked [!doorIsLocked] timeOut() / { lockDoor(); } ;   "
        + "  Locked -> Closed [isAuthorized] unlock() ;"
        + "}");
    assertFalse(parser.hasErrors());
  }
  
  
  @Test
  public void testState() throws IOException {
    parser.parse_StringSCState("state Opened;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState2() throws IOException {
    parser.parse_StringSCState("state Opened { /* … */ };");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState3() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  [!Locked]"
        + "};");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState4() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  entry { ringTheDoorBell(); }"
        + "};");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState5() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  state Ajar;"
        + "  state WideOpen;"
        + "};");
    assertFalse(parser.hasErrors());
  }
  
  
  @Test
  public void testTransition() throws IOException {
    parser.parse_StringSCTransition("Open -> Close close;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition2() throws IOException {
    parser.parse_StringSCTransition("Opened -> Closed close() ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition3() throws IOException {
    parser.parse_StringSCTransition("Closed -> Opened open() / {ringTheDoorBell();};");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition4() throws IOException {
    parser.parse_StringSCTransition("Closed -> Locked [!doorIsLocked] timeOut() / "
        + "          { lockDoor(); }"
        + "          ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition5() throws IOException {
    parser.parse_StringSCTransition("Locked -> Closed [isAuthorized] unlock() ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testInternTransition() throws IOException {
    parser.parse_StringSCInternTransition("-> [!doorIsLocked] timeOut() / { lockDoor(); } ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition6() throws IOException {
    parser.parse_StringSCTransition("Opened -> Closed return ;");
    assertFalse(parser.hasErrors());
  }
}
