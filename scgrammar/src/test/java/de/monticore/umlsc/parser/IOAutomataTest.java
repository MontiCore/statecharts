/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.myioautomata._parser.MyIOAutomataParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class IOAutomataTest {
  
  @Before
  public void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void testBumpControl() throws IOException {
    MyIOAutomataParser p = new MyIOAutomataParser();
    p.parse("src/test/resources/ioautomata/BumpControl.aut");
    assertFalse(p.hasErrors());
  }
  
  @Test
  public void testAnotherAutomatonImpl() throws IOException {
    MyIOAutomataParser p = new MyIOAutomataParser();
    p.parse("src/test/resources/ioautomata/AnotherAutomatonImpl.aut");
    assertFalse(p.hasErrors());
  }
  
  @Test
  public void testInvalidAutomatonBehaviorImpl() throws IOException {
    MyIOAutomataParser p = new MyIOAutomataParser();
    p.parse("src/test/resources/ioautomata/InvalidAutomatonBehaviorImpl.aut");
    assertFalse(p.hasErrors());
  }
  
  @Test
  public void testValidAutomaton2() throws IOException {
    MyIOAutomataParser p = new MyIOAutomataParser();
    p.parse("src/test/resources/ioautomata/ValidAutomaton2.aut");
    assertFalse(p.hasErrors());
  }
  
  @Test
  public void testBumpSpeed() throws IOException {
    MyIOAutomataParser p = new MyIOAutomataParser();
    p.parse("src/test/resources/ioautomata/BumpSpeed.aut");
    assertFalse(p.hasErrors());
  }
  
}
