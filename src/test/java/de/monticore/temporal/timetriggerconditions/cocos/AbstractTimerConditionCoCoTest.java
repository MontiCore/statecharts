/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer.lang.AbstractTest;
import de.monticore.temporal.timetriggerconditions._ast.ASTTimeTriggerCondition;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Parses a single timer condition inside a minimal Statechart and runs one CoCo on it.
 * Subclasses provide the checker and may ask for the concrete condition type.
 */
public abstract class AbstractTimerConditionCoCoTest extends AbstractTest {
  
  /** Asserts that the condition passes its CoCo without any finding. */
  protected void checkValid(String input) throws IOException {
    checker().checkAll(parse(input));
    
    assertTrue(Log.getFindings().isEmpty(), input + " produced " + Log.getFindings());
  }
  
  /** Asserts that the condition reports the given code as an error with a source position. */
  protected void checkInvalid(String input, String expectedErrorCode) throws IOException {
    Finding finding = check(input, expectedErrorCode);
    
    assertTrue(finding.isError(), input);
    assertTrue(finding.getSourcePosition().isPresent(), input);
  }
  
  /** Asserts that the condition reports the given code as a warning with a source position. */
  protected void checkWarning(String input, String expectedWarningCode) throws IOException {
    Finding finding = check(input, expectedWarningCode);
    
    assertTrue(finding.isWarning(), input);
    assertTrue(finding.getSourcePosition().isPresent(), input);
  }
  
  /** Runs the CoCo and returns the first finding carrying the expected code. */
  protected Finding check(String input, String expectedCode) throws IOException {
    checker().checkAll(parse(input));
    
    return Log.getFindings().stream().filter(candidate -> candidate.getMsg().contains(expectedCode))
        .findFirst().orElseThrow(() -> new AssertionError(input + " produced " + Log
            .getFindings()));
  }
  
  /** Parses the condition and returns it as the concrete AST type the subclass expects. */
  protected <T extends ASTTimeTriggerCondition> T parse(String input, Class<T> type)
      throws IOException {
    return assertInstanceOf(type, parse(input), input);
  }
  
  /** Parses the condition inside a minimal Statechart and returns the timer condition. */
  protected ASTTimeTriggerCondition parse(String input) throws IOException {
    ASTSCArtifact statechart = parseStringModel("""
        statechart TimerConditionTest {
          initial state A;
          state B;
          A -> B %s;
        }
        """.formatted(input));
    
    List<ASTTimeTriggerCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            conditions.add(node.getTimerCondition());
          }
          
        });
    statechart.accept(traverser);
    
    assertFalse(conditions.isEmpty(), "No TimerCondition in: " + input);
    return conditions.get(0);
  }
  
  protected abstract TimeTriggerConditionsCoCoChecker checker();
  
}
