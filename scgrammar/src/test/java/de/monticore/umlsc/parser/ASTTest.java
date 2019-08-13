/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.parser;

import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ASTTest {



  @Test
  public void testAST() {
    StatechartWithJavaParser parser = new StatechartWithJavaParser();

    try {
      Optional<ASTSCArtifact> scDef = parser.parse("src/test/resources/de/monticore/umlsc/examples/TestInnerTransitions.sc");

      assertEquals(2,scDef.get().getStatechart().getSCStateList().get(0).getSCTransitionList().size());
      assertEquals(2,scDef.get().getStatechart().getSCStateList().get(0).getSCInternTransitionList().size());

    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
