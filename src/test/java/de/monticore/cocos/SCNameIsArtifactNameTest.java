/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._cocos.SCNameIsArtifactName;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SCNameIsArtifactNameTest extends GeneralAbstractTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @Test
  public void testCoCOInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/invalid/InvalidSCName.sc");
    assertTrue("InvalidSCName.sc could not be parsed",  ast.isPresent());
    check(ast.get());
    assertEquals(1, Log.getFindingsCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains(
        SCNameIsArtifactName.ERROR_CODE)));
    
  }
  
  @Test
  public void testCoCOValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Door.sc");
    assertTrue("Door.sc could not be parsed",  ast.isPresent());
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  
  
  @Test
  public void testCoCoValidCar() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Car.sc");
    assertTrue("Car.sc could not be parsed",  ast.isPresent());
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValid2() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/Test.sc");
    assertTrue("Test.sc could not be parsed",  ast.isPresent());
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  
  protected void check(ASTSCArtifact ast) {
    IUMLStatechartsArtifactScope scope = UMLStatechartsMill.scopesGenitorDelegator()
        .createFromAST(ast);
    scope.setName("Dummy");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new SCNameIsArtifactName());
    checker.checkAll(ast);
  }
}
