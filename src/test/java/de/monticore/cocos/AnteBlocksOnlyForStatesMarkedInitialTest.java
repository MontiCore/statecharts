/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code.SCTransitions4CodeMill;
import de.monticore.sctransitions4code._cocos.AnteBlocksOnlyForStatesMarkedInitial;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static de.monticore.sctransitions4code._cocos.AnteBlocksOnlyForStatesMarkedInitial.ERROR_CODE;

public class AnteBlocksOnlyForStatesMarkedInitialTest extends GeneralAbstractTest {

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
      .filter(Finding::isWarning)
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
  public void testCoCoValid4(String model) throws IOException {
    // When && Then
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // Then
    checkExpectedErrors(ast, new ArrayList<>());
  }

  @Test
  public void testCoCoInvalid1() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  { initS(); } state S; " +
        "}";

    // When && Then
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));
    checkExpectedErrors(ast, List.of(ERROR_CODE));
  }

  @Test
  public void testCoCoInvalid2() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  final { initS(); } state S; " +
        "}";

    // When && Then
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));
    checkExpectedErrors(ast, List.of(ERROR_CODE));
  }

  @Test
  public void testCoCoInvalid3() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  state S1 { " +
        "    { initS1S1(); } state S1S1; " +
        "  }; " +
        "}";

    // When && Then
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));
    checkExpectedErrors(ast, List.of(ERROR_CODE));
  }

  @Test
  public void testCoCoInvalid4() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  { initS(); } state S1; " +
        "  { initS(); } state S2; " +
        "}";

    // When && Then
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));
    checkExpectedErrors(ast, List.of(ERROR_CODE, ERROR_CODE));
  }
}
