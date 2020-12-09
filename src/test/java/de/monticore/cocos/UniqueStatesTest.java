/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore._cocos.UniqueStates;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniqueStatesTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.init();
  }
  
  @Test
  public void testCoCOInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/invalid/NonUnique.sc");
    assertTrue("NonUnique.sc could not be parsed",  ast.isPresent());
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker().addCoCo(new UniqueStates());
    checker.checkAll(ast.get());
    assertEquals(1, Log.getErrorCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains(UniqueStates.ERROR_CODE)));
    
  }
  
  @Test
  public void testCoCOValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/Door.sc");
    assertTrue("Door.sc could not be parsed",  ast.isPresent());
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker().addCoCo(new UniqueStates());
    checker.checkAll(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
}
