/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.flatsc._parser.FlatSCParser;
import de.monticore.myhiersc._parser.MyHierSCParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class FlatSCParserTest {
  
  FlatSCParser parser = new FlatSCParser();
  
  @Before
  public void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void testStatechart() throws IOException {
   parser.parse_StringSCArtifact("statechart Foo {"
        + "  state Bla "
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart2() throws IOException {
    parser.parse_StringSCArtifact("statechart Door2 {"
        + "  initial state Opened "
        + "  state Closed"
        + "  Opened -> Closed"
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart3() throws IOException {
    parser.parse_StringSCArtifact("(c) <<test>> statechart Door2 {"
        + "  state Opened "
        + "  state Closed"
        + "  Opened -> Closed"
        + "}");
    check(parser);
  }
  
  protected void check(FlatSCParser parser) {
    if (parser.hasErrors()) {
      for(Finding f : LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}
