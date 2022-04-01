/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import com.google.common.collect.Lists;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._cocos.AnteBlocksOnlyForInitialStates;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AnteBlocksOnlyForInitialStatesTest {

  private static final TriggeredStatechartsParser parser = new TriggeredStatechartsParser();

  private static final TriggeredStatechartsCoCoChecker checker = new TriggeredStatechartsCoCoChecker();

  @BeforeClass
  public static void beforeClass() {
    LogStub.init();
    Log.enableFailQuick(false);
    TriggeredStatechartsMill.init();

    checker.addCoCo(new AnteBlocksOnlyForInitialStates());
  }

  @Before
  public void clear(){
    Log.clearFindings();
    TriggeredStatechartsMill.globalScope().clear();
    BasicSymbolsMill.initializePrimitives();
  }

  protected void checkExpectedErrors(ASTSCArtifact stateChart, List<String> expectedErrorCodes) {
    Log.getFindings().clear();

    // When
    checker.checkAll(stateChart);

    // Then
    List<String> actualErrors = Log.getFindings().stream()
      .filter(Finding::isError)
      .map(err -> err.getMsg().split(" ")[0])
      .collect(Collectors.toList());
    assertEquals(expectedErrorCodes, actualErrors);
  }


  @Test
  public void testCocoValid4Ante() throws IOException {
    ASTSCArtifact ast = parser.parse("src/test/resources/valid/ValidWithAnte.sc").get();
    checkExpectedErrors(ast, new ArrayList<>());
  }

  @Test
  public void testCocoValid4NoAnte() throws IOException {
    ASTSCArtifact ast = parser.parse("src/test/resources/valid/ValidWithoutAnte.sc").get();
    checkExpectedErrors(ast, new ArrayList<>());
  }

  @Test
  public void testCocoValid4StereoAnte() throws IOException {
    ASTSCArtifact ast = parser.parse("src/test/resources/valid/ValidWithStereoAnte.sc").get();
    checkExpectedErrors(ast, new ArrayList<>());
  }

  @Test
  public void testCocoInvalid4AnteWithoutInitial() throws IOException {
    ASTSCArtifact ast = parser.parse("src/test/resources/invalid/AnteWithoutInitialState.sc").get();
    List<String> expectedErrors = Lists.newArrayList(
      AnteBlocksOnlyForInitialStates.ERROR_CODE, AnteBlocksOnlyForInitialStates.ERROR_CODE,
      AnteBlocksOnlyForInitialStates.ERROR_CODE, AnteBlocksOnlyForInitialStates.ERROR_CODE
    );

    checkExpectedErrors(ast, expectedErrors);
  }
}
