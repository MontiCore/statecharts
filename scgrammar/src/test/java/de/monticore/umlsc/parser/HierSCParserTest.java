/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.myhiersc._parser.MyHierSCParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class HierSCParserTest {
  
  MyHierSCParser parser = new MyHierSCParser();
  
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
        + "  Opened -> Closed close() ;"
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart3() throws IOException {
    parser.parse_StringStatechart("statechart Door1 { state Opened; }");
    check(parser);
  }
  
  @Test
  public void testStatechart4() throws IOException {
    parser.parse_StringStatechart("statechart Door3 {"
        + "  Closed -> Opened open() ;"
        + "}");
    check(parser);
  }
  
  @Test
  public void testStatechart5() throws IOException {
    parser.parse_StringStatechart("statechart Door1 {"
        + "  state Opened {"
        + "    state Ajar;"
        + "  };"
        + "}");
    check(parser);
  }
  
  @Test
  public void testTransition() throws IOException {
    parser.parse_StringSCTransition(" Closed -> Opened open() ;");
    check(parser);
  }
  
  protected void check(MyHierSCParser parser) {
    if (parser.hasErrors()) {
      for(Finding f : LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}