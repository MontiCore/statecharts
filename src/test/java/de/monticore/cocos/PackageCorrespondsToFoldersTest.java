/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._cocos.PackageCorrespondsToFolders;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._cocos.UMLStatechartsCoCoChecker;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageCorrespondsToFoldersTest extends GeneralAbstractTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  @Test
  public void testCoCOInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/invalid/InvalidPackage.sc");
    assertTrue(ast.isPresent(), "InvalidPackage.sc could not be parsed");
    check(ast.get());
    assertEquals(1, Log.getErrorCount());
    assertTrue(Log.getFindings().stream().anyMatch(n -> n.getMsg().contains(PackageCorrespondsToFolders.ERROR_CODE)));
    
  }
  
  @Test
  public void testCoCOValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Door.sc");
    assertTrue(ast.isPresent(), "Door.sc could not be parsed");
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  
  
  @Test
  public void testCoCoValidCar() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/examples/uml/Car.sc");
    assertTrue(ast.isPresent(), "Car.sc could not be parsed");
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValidPackage() throws IOException {
    Optional<ASTSCArtifact> ast = parser
        .parse("src/test/resources/valid/ValidPackage.sc");
    assertTrue(ast.isPresent(), "ValidPackage.sc could not be parsed");
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  @Test
  public void testCoCoValidPackageTrig() throws IOException {
    Optional<ASTSCArtifact> ast = new TriggeredStatechartsParser()
        .parse("src/test/resources/valid/ValidPackage.sc");
    assertTrue(ast.isPresent(), "ValidPackage.sc could not be parsed");
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  protected void check(ASTSCArtifact ast) {
    IUMLStatechartsArtifactScope scope = UMLStatechartsMill.scopesGenitorDelegator()
        .createFromAST(ast);
    scope.setName("Dummy");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new PackageCorrespondsToFolders());
    checker.checkAll(ast);
  }
}
