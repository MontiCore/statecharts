/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.sc.mysc._parser.MySCParser;
import de.monticore.sc.sccore._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

public class MySCParserTest {
  
  @Before
  public void init(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void doTest() throws IOException {
    MySCParser parser = new MySCParser();
    Optional<ASTSCArtifact> scDef = parser.parse_StringSCArtifact("statechart Foo {"
        + "  state Bla {"
        + "    state S"
        + "  }"
        + "}");
    if (parser.hasErrors()) {
      for(Finding f :LogStub.getFindings()){
        System.out.println(f.buildMsg());
      };
    }
    assertFalse(parser.hasErrors());
  }
}
