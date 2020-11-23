/* (c) https://github.com/MontiCore/monticore */
package de.monticore.symboltable;

import de.monticore.StatechartsCLI;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._symboltable.SCStateSymbol;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class ResolvingTest {
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.initPlusLog();
  }
  
  @Before
  public void setUp() throws Exception {
    Log.clearFindings();
    UMLStatechartsMill.globalScope().clear();
  }
  
  @Test
  public void testResolvingState() {
    StatechartsCLI tool = new StatechartsCLI();
    ASTSCArtifact ast = tool.parseFile("src/test/resources/valid/Test.sc");
    IUMLStatechartsArtifactScope st = tool.createSymbolTable(ast);
    Optional<SCStateSymbol> stateSymbol = st.resolveSCState("Parking");
    assertTrue("Could not resolve state Parking", stateSymbol.isPresent());
  }
}
