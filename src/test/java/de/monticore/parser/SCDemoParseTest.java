/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
  public void testTeaser2() throws IOException {
    parser.parse("src/test/resources/examples/Car.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTeaser3() throws IOException {
    parser.parse("src/test/resources/examples/Door2.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTestSC() throws IOException {
    parser.parse("src/test/resources/valid/Test.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTest2SC() throws IOException {
    parser.parse("src/test/resources/valid/Test2.sc");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTestAnotherAutomatonImpl() throws IOException {
    parser.parse("src/test/resources/ioautomata/AnotherAutomatonImpl.aut");
    assertFalse(parser.hasErrors());
  }
  
  @Test @Ignore
  public void testTestBumpControl() throws IOException {
    parser.parse("src/test/resources/ioautomata/BumpControl.aut");
    assertFalse(parser.hasErrors());
  }
  
  @Test @Ignore
  public void testTestBumpSpeed() throws IOException {
    parser.parse("src/test/resources/ioautomata/BumpSpeed.aut");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTestInvalidAutomatonBehaviorImpl() throws IOException {
    parser.parse("src/test/resources/ioautomata/InvalidAutomatonBehaviorImpl.aut");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTestValidAutomaton2() throws IOException {
    parser.parse("src/test/resources/ioautomata/ValidAutomaton2.aut");
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
        + "    do / { ringTheDoorBell(); }"
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
    parser.parse_StringSCState("state Opened  [!Locked] {} ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testState4() throws IOException {
    parser.parse_StringSCState("state Opened {"
        + "  entry / { ringTheDoorBell(); }"
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
    parser.parse_StringSCTransition("Opened -> Closed close ;");
    assertFalse(parser.hasErrors());
  }
  
  @Test
  public void testTransition7() throws IOException {
    parser.parse_String("import a.b.Person;\n"
        + "\n"
        + "statechart Door4 {\n"
        + "  Closed -> Opened open() / { String foo = age;};\n"
        + "}\n");
    assertFalse(parser.hasErrors());
  }
}
