/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._cocos.MaxOneInitialState;
import de.monticore.scstatehierarchy.NoSubstatesHandler;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MaxOneInitialStateTest {

  protected UMLStatechartsParser parser = new UMLStatechartsParser();

  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.init();
    UMLStatechartsMill.init();
  }

  @Before
  public void clear(){
    Log.clearFindings();
    UMLStatechartsMill.globalScope().clear();
  }

  @Test
  public void testCoCOInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
      .parse("src/test/resources/invalid/TwoInitialStates.sc");
    assertTrue("NoInitialState.sc could not be parsed",  ast.isPresent());
    check(ast.get());
    assertEquals(1, Log.getErrorCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains(MaxOneInitialState.ERROR_CODE)));

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

  protected void check(ASTSCArtifact ast) {
    IUMLStatechartsArtifactScope scope = UMLStatechartsMill.scopesGenitorDelegator()
      .createFromAST(ast);
    scope.setName("Dummy");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new MaxOneInitialState());
    checker.checkAll(ast);
  }
}
