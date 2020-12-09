/* (c) https://github.com/MontiCore/monticore */
package de.monticore.symboltable;

import de.monticore.StatechartsCLI;
import de.monticore.io.paths.ModelPath;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._symboltable.SCStateSymbol;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsGlobalScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;
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
    UMLStatechartsMill.init();
    UMLStatechartsMill.globalScope().clear();
  }
  
  @Test
  public void testResolvingState() {
    StatechartsCLI tool = new StatechartsCLI();
    ASTSCArtifact ast = tool.parseFile("src/test/resources/valid/Test.sc");
    IUMLStatechartsArtifactScope st = tool.createSymbolTable(ast);
    st.setName("Test");
    Optional<SCStateSymbol> stateSymbol = st.resolveSCState("Parking");
    assertTrue("Could not resolve state Parking", stateSymbol.isPresent());
  }
  
  @Test
  public void testResolvingState2() {
    IUMLStatechartsGlobalScope gs = UMLStatechartsMill
        .globalScope();
    gs.setFileExt("sc");
    gs.setModelPath(new ModelPath(Paths.get("src/test/resources/symtab")));
    Optional<SCStateSymbol> stateSymbol = gs.resolveSCState("Test2.Parking");
    assertTrue("Could not resolve state Parking", stateSymbol.isPresent());
  }
}
