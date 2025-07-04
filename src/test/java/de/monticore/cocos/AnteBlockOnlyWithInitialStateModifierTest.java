/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._cocos.AnteBlockOnlyWithInitialStateModifier;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

import static de.monticore.sctransitions4code._cocos.AnteBlockOnlyWithInitialStateModifier.ERROR_CODE;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnteBlockOnlyWithInitialStateModifierTest extends GeneralAbstractTest {

  private static final TriggeredStatechartsCoCoChecker checker = new TriggeredStatechartsCoCoChecker();

  @BeforeAll
  public static void beforeClass() {
    checker.addCoCo(new AnteBlockOnlyWithInitialStateModifier());
  }

  @BeforeEach
  public void initMill(){
    initTriggeredStatechartsMill();
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
  public void testCoCoValid(String model) throws IOException {
    // Given
    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(0, Log.getFindings().size(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid1() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  { initS(); } state S; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(1, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid2() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  final { initS(); } state S; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(1, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid3() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  { initS(); } state S1; " +
        "  { initS(); } state S2; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(2, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE, ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid4() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  state S1 { " +
        "    { initS1S1(); } state S1S1; " +
        "  }; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(1, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid5() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  state S1 { " +
        "    { initS1S1(); } state S1S1; " +
        "    { initS1S2(); } state S1S2; " +
        "  }; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(2, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE, ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }

  @Test
  public void testCoCoInvalid6() throws IOException {
    // Given
    String model =
      "statechart SC { " +
        "  state S1 { " +
        "    { initS1S1(); } state S1S1; " +
        "  }; " +
        "  state S2 { " +
        "    { initS2S1(); } state S2S1; " +
        "  }; " +
        "}";

    ASTSCArtifact ast = TriggeredStatechartsMill.parser().parse_StringSCArtifact(model)
      .orElseThrow(() -> new IllegalArgumentException("Findings: " + Log.getFindings()));

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(2, Log.getFindings().size(), () -> Log.getFindings().toString());
    assertArrayEquals(new String[] {ERROR_CODE, ERROR_CODE}, getLoggedErrorCodes(), () -> Log.getFindings().toString());
  }
}
