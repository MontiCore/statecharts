/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import com.google.common.collect.Lists;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._cocos.TransitionPreconditionsAreBoolean;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.monticore.types.FullTriggeredStatechartsDeriver;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TransitionPreconditionsAreBooleanTest {

  protected final TriggeredStatechartsParser parser = new TriggeredStatechartsParser();

  @BeforeClass
  public static void beforeClass() {
    LogStub.init();
    TriggeredStatechartsMill.init();
  }

  @Before
  public void clear(){
    Log.clearFindings();
    TriggeredStatechartsMill.globalScope().clear();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  public void testCocoValid() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/valid/ValidTransitionPrecondition.sc").get();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean(new FullTriggeredStatechartsDeriver()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(0, Log.getErrorCount());
  }

  @Test
  public void testCocoInvalid() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/invalid/InvalidTransitionPrecondition.sc").get();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean(new FullTriggeredStatechartsDeriver()));

    // When
    checker.checkAll(ast);

    // Then
    List<String> findings = Log.getFindings().stream()
      .filter(Finding::isError)
      .map(finding -> finding.getMsg().substring(0, TransitionPreconditionsAreBoolean.ERROR_CODE.length()))
      .collect(Collectors.toList());

    assertEquals(
      Lists.newArrayList(TransitionPreconditionsAreBoolean.ERROR_CODE, TransitionPreconditionsAreBoolean.ERROR_CODE),
      findings);
  }
}
