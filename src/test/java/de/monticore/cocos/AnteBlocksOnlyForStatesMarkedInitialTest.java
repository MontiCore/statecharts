/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._cocos.AnteBlocksOnlyForStatesMarkedInitial;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnteBlocksOnlyForStatesMarkedInitialTest extends GeneralAbstractTest {

  private static final TriggeredStatechartsParser parser = new TriggeredStatechartsParser();

  private static final TriggeredStatechartsCoCoChecker checker = new TriggeredStatechartsCoCoChecker();

  @BeforeAll
  public static void beforeClass() {
    checker.addCoCo(new AnteBlocksOnlyForStatesMarkedInitial());
  }


  @Override
  @BeforeEach
  public void setUp(){
    initLogger();
    initTriggeredStatechartsMill();
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

  @ParameterizedTest
  @ValueSource(strings = {
    "statechart SC1 { " +
      "  state S; " +
      "}",
    "statechart SC2 { " +
      "  final state S; " +
      "}",
    "statechart SC3 { " +
      "  initial { initS(); } state S; " +
      "}",
    "statechart SC4 { " +
      "  state S1 { " +
      "    initial { initS1S1(); } state S1S1; " +
      "  }; " +
      "}",
  })
  public void testCocoValid4(String model) throws IOException {
    // When && Then
    ASTSCArtifact ast = parser.parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // Then
    checkExpectedErrors(ast, new ArrayList<>());
  }

  public void testCocoInvalid1(l) throws IOException {
    // Given
    String model =
      "";

    // When && Then
    ASTSCArtifact ast = parser.parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));
    checkExpectedErrors(ast, new ArrayList<>());
  }
}
