/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._cocos.UniqueStates;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UniqueStatesTest extends GeneralAbstractTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @Test
  public void testCoCoInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/invalid/NonUnique.sc");
    assertTrue("NonUnique.sc could not be parsed",  ast.isPresent());
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new UniqueStates());
    checker.checkAll(ast.get());
    assertEquals(1, Log.getErrorCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains(UniqueStates.ERROR_CODE)));
    
  }
  
  @Test
  public void testCoCoValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Door.sc");
    assertTrue("Door.sc could not be parsed",  ast.isPresent());
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new UniqueStates());
    checker.checkAll(ast.get());
    if(Log.getErrorCount() >0){
      Log.printFindings();
    }
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValidCar() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Car.sc");
    assertTrue("Car.sc could not be parsed",  ast.isPresent());
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new UniqueStates());
    checker.checkAll(ast.get());
    if(Log.getErrorCount() >0){
      Log.printFindings();
    }
    assertEquals(0, Log.getErrorCount());
    
  }
}
