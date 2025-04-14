/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scevents._cocos.NonCapitalEventNames;
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

public class NonCapitalEventNamesTest extends GeneralAbstractTest {
  
  protected UMLStatechartsParser parser = new UMLStatechartsParser();
  
  @Test
  public void testCoCOInvalid() throws IOException {
    Optional<ASTSCArtifact> ast = parser.parse("src/test/resources/invalid/InvalidEventName.sc");
    assertTrue(ast.isPresent(), "InvalidEventName.foo could not be parsed");
    check(ast.get());
    assertEquals(1, Log.getFindings().size());
    assertTrue(Log.getFindings().stream()
        .anyMatch(n -> n.getMsg().contains(NonCapitalEventNames.ERROR_CODE)));
    
  }
  
  @Test
  public void testCoCoValid() throws IOException {
    Optional<ASTSCArtifact> ast = parser.parse("src/test/resources/valid/Test2.sc");
    assertTrue(ast.isPresent(), "Test2.sc could not be parsed");
    check(ast.get());
    assertEquals(0, Log.getErrorCount());
    
  }
  
  protected void check(ASTSCArtifact ast) {
    IUMLStatechartsArtifactScope scope =
        UMLStatechartsMill.scopesGenitorDelegator().createFromAST(ast);
    scope.setName("Dummy");
    UMLStatechartsCoCoChecker checker = new UMLStatechartsCoCoChecker();
    checker.addCoCo(new NonCapitalEventNames());
    checker.checkAll(ast);
  }
}
