/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import de.monticore.myflatsc._parser.MyFlatSCParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertFalse;

public class FlatSCParserTest {
  
  MyFlatSCParser parser = new MyFlatSCParser();
  
  @Before
  public void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void testStatechart() throws IOException {
   parser.parse_StringSCArtifact("statechart Foo {"
        + "  state Bla; "
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart2() throws IOException {
    parser.parse_StringSCArtifact("statechart Door2 {"
        + "  initial state Opened; "
        + "  state Closed;"
        + "  Opened -> Closed close;"
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart3() throws IOException {
    parser.parse_StringSCArtifact("<<test>> statechart Door2 {"
        + "  state Opened; "
        + "  state Closed;"
        + "  Opened -> Closed close;"
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart4() throws IOException {
    parser.parse_StringSCArtifact("package sc;"
        + ""
        + "statechart Door1 { "
        + "  state Opened;"
        + "  state Closed; "
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart5() throws IOException {
    parser.parse_StringSCArtifact("import java.util.List;"
        + ""
        + "statechart Door2 {"
        + "  initial state Opened; "
        + "  state Closed;"
        + "  Opened -> Closed close;"
        + "}");
    check(parser);
  }
  
  protected void check(MyFlatSCParser parser) {
    if (parser.hasErrors()) {
      for(Finding f : LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}
