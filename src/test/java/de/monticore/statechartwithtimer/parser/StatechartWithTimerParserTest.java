/* (c) https://github.com/MontiCore/monticore */
package de.monticore.statechartwithtimer.parser;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;
import de.monticore.statechartwithtimer.lang.AbstractTest;
import de.monticore.temporal.timetriggerconditions._ast.ASTAfterCondition;
import de.monticore.temporal.timetriggerconditions._ast.ASTEveryTimeCondition;
import de.monticore.temporal.timetriggerconditions._ast.ASTOnCondition;
import de.monticore.temporal.timetriggerconditions._cocos.AfterConditionHasValidDuration;
import de.monticore.temporal.timetriggerconditions._cocos.EveryTimeConditionHasValidRepetitions;
import de.monticore.temporal.timetriggerconditions._cocos.OnConditionHasValidValue;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatechartWithTimerParserTest extends AbstractTest {
  
  @Test
  public void testMyStatechart() {
    ASTSCArtifact a = parseModel("src/test/resources/statechartwithtimer/parser/MyStatechart.sc");
    assertNotNull(a);
  }
  
  @Test
  public void testTimerStatechart() {
    ASTSCArtifact a = parseModel(
        "src/test/resources/statechartwithtimer/parser/TimerStatechart.sc");
    assertNotNull(a);
  }
  
  @Test
  public void testOnConditionStatechart() {
    ASTSCArtifact a = parseModel("src/test/resources/statechartwithtimer/parser/OnStatechart.sc");
    assertNotNull(a);
  }
  
  @ParameterizedTest(name = "supported after unit: {0}")
  @ValueSource(strings = { "after 10ms", "after 10s", "after 5min", "after 1h", "after 2d" })
  public void testAllSupportedAfterTimeUnits(String input) {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith(input));
    
    assertFalse(findAfterConditions(statechart).isEmpty(), input);
  }
  
  @Test
  public void testDecimalAfterDuration() {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith("after 1.5s"));
    assertFalse(findAfterConditions(statechart).isEmpty());
  }
  
  @Test
  public void testIsoAfterPeriod() {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith("after PT10S"));
    assertFalse(findIsoAfterConditions(statechart).isEmpty());
  }
  
  @Test
  public void testMultipleAfterTransitionsFromSameState() {
    ASTSCArtifact statechart = parseStringModel("""
        statechart MultipleTimers {
          initial state A;
          state B;
          state C;
          A -> B after 5s;
          A -> C after 10s;
        }
        """);
    
    assertEquals(2, findAfterConditions(statechart).size());
  }
  
  @Test
  public void testMalformedAfterDoesNotParse() throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    parser.parse_StringSCArtifact(timerStatechartWith("after"));
    
    assertTrue(parser.hasErrors());
  }
  
  @ParameterizedTest(name = "malformed ISO period: {0}")
  @ValueSource(strings = { "after P1W2D", "after -PT1S", "after PX" })
  public void testMalformedIsoAfterPeriodsDoNotParse(String condition) throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    var result = parser.parse_StringSCArtifact(timerStatechartWith(condition));
    
    assertTrue(parser.hasErrors() || result.isEmpty(), condition);
  }
  
  @ParameterizedTest(name = "supported on date-time: {0}")
  @ValueSource(strings = { "on 2026-07-20T12:00:00Z", "on 2026-07-20T12:00Z",
      "on 2026-07-20T12:00:00.500Z", "on 2026-07-20T12:00:00+02:00", "on 0001-01-01T00:00:00Z",
      "on 9999-12-31T23:59:59Z" })
  public void testValidOnConditionDateTimeStringsParse(String input) {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith(input));
    
    assertFalse(findOnConditions(statechart).isEmpty(), input);
  }
  
  @Test
  public void testMissingOffsetIsRejectedByCoCo() {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith("on 2026-07-20T12:00:00"));
    
    findOnConditions(statechart).forEach(this::checkOnCondition);
    
    assertTrue(Log.getFindings().stream().anyMatch(finding -> finding.getMsg().contains(
        OnConditionHasValidValue.INVALID_DATE_TIME)));
  }
  
  @ParameterizedTest(name = "supported every condition: {0}")
  @ValueSource(strings = { "every 10s", "every 5min 3 times", "from 2026-07-20T12:00:00Z every 10s",
      "from 2026-07-20T12:00:00Z every 10s 5 times" })
  public void testEveryTimeConditionParses(String input) {
    ASTSCArtifact statechart = parseStringModel(timerStatechartWith(input));
    
    assertFalse(findEveryTimeConditions(statechart).isEmpty(), input);
  }
  
  @Test
  public void testInvalidEveryTimeConditionCoCoOnStatechart() {
    ASTSCArtifact statechart = parseStringModel("""
        statechart InvalidTimerStatechart {
          initial state A;
          state B;
          A -> B every 10s 0 times;
        }
        """);
    
    findEveryTimeConditions(statechart).forEach(this::checkEveryTimeCondition);
    
    assertTrue(Log.getFindings().stream().anyMatch(finding -> finding.getMsg().contains(
        EveryTimeConditionHasValidRepetitions.NON_POSITIVE_REPETITIONS)));
  }
  
  @Test
  public void testTimerConditionCoCoOnStatechart() {
    ASTSCArtifact statechart = parseModel(
        "src/test/resources/statechartwithtimer/parser/TimerStatechart.sc");
    List<ASTAfterCondition> conditions = findAfterConditions(statechart);
    
    assertFalse(conditions.isEmpty());
    conditions.forEach(this::checkAfterCondition);
    assertTrue(Log.getFindings().isEmpty());
  }
  
  @Test
  public void testInvalidTimerConditionCoCoOnStatechart() {
    ASTSCArtifact statechart = parseStringModel("""
        statechart InvalidTimerStatechart {
          initial state A;
          state B;
          A -> B after 0s;
        }
        """);
    
    findAfterConditions(statechart).forEach(this::checkAfterCondition);
    
    assertTrue(Log.getFindings().stream().anyMatch(finding -> finding.getMsg().contains(
        AfterConditionHasValidDuration.NON_POSITIVE_DURATION)));
  }
  
  protected List<ASTAfterCondition> findAfterConditions(ASTSCArtifact statechart) {
    List<ASTAfterCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTAfterCondition condition
                && condition.isPresentDuration()) {
              conditions.add(condition);
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected List<ASTAfterCondition> findIsoAfterConditions(ASTSCArtifact statechart) {
    List<ASTAfterCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTAfterCondition condition
                && condition.isPresentPeriod()) {
              conditions.add(condition);
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected void checkAfterCondition(ASTAfterCondition condition) {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new AfterConditionHasValidDuration());
    checker.checkAll(condition);
  }
  
  protected List<ASTOnCondition> findOnConditions(ASTSCArtifact statechart) {
    List<ASTOnCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTOnCondition) {
              conditions.add((ASTOnCondition) node.getTimerCondition());
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected void checkOnCondition(ASTOnCondition condition) {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new OnConditionHasValidValue());
    checker.checkAll(condition);
  }
  
  protected List<ASTEveryTimeCondition> findEveryTimeConditions(ASTSCArtifact statechart) {
    List<ASTEveryTimeCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTEveryTimeCondition) {
              conditions.add((ASTEveryTimeCondition) node.getTimerCondition());
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected void checkEveryTimeCondition(ASTEveryTimeCondition condition) {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new EveryTimeConditionHasValidRepetitions());
    checker.checkAll(condition);
  }
  
  protected String timerStatechartWith(String event) {
    return """
        statechart TimerParserTest {
          initial state A;
          state B;
          A -> B %s;
        }
        """.formatted(event);
  }
  
}
