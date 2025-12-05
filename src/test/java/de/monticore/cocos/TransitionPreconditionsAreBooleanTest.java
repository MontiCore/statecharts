/* (c) https://github.com/MontiCore/monticore */
package de.monticore.cocos;

import com.google.common.collect.Lists;
import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.sctransitions4code._cocos.TransitionPreconditionsAreBoolean;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.oosymbols._symboltable.OOTypeSymbol;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._cocos.TriggeredStatechartsCoCoChecker;
import de.monticore.triggeredstatecharts._parser.TriggeredStatechartsParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static de.monticore.sctransitions4code._cocos.TransitionPreconditionsAreBoolean.GUARD_NOT_BOOLEAN_ERROR_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransitionPreconditionsAreBooleanTest extends GeneralAbstractTest {

  protected final TriggeredStatechartsParser parser = new TriggeredStatechartsParser();

  @Override
  @BeforeEach
  public void setUp() {
    initLogger();
    initTriggeredStatechartsMill();
    BasicSymbolsMill.initializePrimitives();
    loadString();
  }

  public static void loadString() {
    OOTypeSymbol string = TriggeredStatechartsMill.oOTypeSymbolBuilder()
      .setName("String")
      .setSpannedScope(TriggeredStatechartsMill.scope())
      .build();

    TriggeredStatechartsMill.globalScope().add(string);
    string.setEnclosingScope(TriggeredStatechartsMill.globalScope());
  }

  @Test
  public void testCocoValid() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/valid/ValidTransitionPrecondition.sc").orElseThrow();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean());

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(0, Log.getErrorCount(), Log.getFindings().toString());
  }

  @Test
  public void testCocoInvalidNotBoolean() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/invalid/InvalidTransitionPrecondition.sc").orElseThrow();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean());

    // When
    checker.checkAll(ast);

    // Then
    List<String> findings = Log.getFindings().stream()
      .filter(Finding::isError)
      .map(finding -> finding.getMsg().substring(0, GUARD_NOT_BOOLEAN_ERROR_CODE.length()))
      .collect(Collectors.toList());

    assertEquals(
      Lists.newArrayList(GUARD_NOT_BOOLEAN_ERROR_CODE, GUARD_NOT_BOOLEAN_ERROR_CODE),
      findings);
  }

  @Test
  public void testCocoInvalidConditionIsTypeReference() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/invalid/InvalidTransitionPreconditionIsTypeReference.sc").orElseThrow();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean());

    // When
    checker.checkAll(ast);

    // Then
    List<String> findings = Log.getFindings().stream()
      .filter(Finding::isError)
      .map(finding -> finding.getMsg().substring(0, "0xFD118".length()))
      .collect(Collectors.toList());

    assertEquals(Lists.newArrayList("0xFD118"), findings);
  }

  @Test
  public void testCocoConditionHasObscureType() throws IOException {
    // Given
    ASTSCArtifact ast = parser.parse("src/test/resources/invalid/TransitionPreconditionIsObscure.sc").orElseThrow();
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(ast).setName("DummyScopeName");

    TriggeredStatechartsCoCoChecker checker =  new TriggeredStatechartsCoCoChecker();
    checker.addCoCo(new TransitionPreconditionsAreBoolean());

    // When
    checker.checkAll(ast);

    // Then
    assertEquals(1, Log.getFindingsCount());
    // Only print the error that the variable symbol can not be found:
    assertEquals("0xFD118", Log.getFindings().get(0).getMsg().substring(0, 7));
  }
}
