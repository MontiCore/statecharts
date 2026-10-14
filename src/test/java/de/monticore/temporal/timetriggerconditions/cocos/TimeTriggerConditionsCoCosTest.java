/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._cocos.StatechartWithTimerCoCoChecker;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;
import de.monticore.temporal.timetriggerconditions._cocos.AfterConditionHasValidDuration;
import de.monticore.temporal.timetriggerconditions._cocos.AfterConditionHasValidPeriod;
import de.monticore.temporal.timetriggerconditions._cocos.CronExpressionIsValid;
import de.monticore.temporal.timetriggerconditions._cocos.EveryTimeConditionHasValidRepetitions;
import de.monticore.temporal.timetriggerconditions._cocos.OnConditionHasValidValue;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCos;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeTriggerConditionsCoCosTest {
  
  @BeforeEach
  public void setUp() {
    StatechartWithTimerMill.reset();
    StatechartWithTimerMill.init();
    LogStub.init();
    Log.enableFailQuick(false);
    Log.getFindings().clear();
  }
  
  @Test
  public void testConfiguredCheckerRunsAllConditionCoCos() throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    var result = parser.parse_StringSCArtifact("""
        statechart InvalidTimerConditions {
          initial state A;
          state B;
        
          A -> B after 0s;
          A -> B after PT0S;
          A -> B on 2025-02-29T12:00:00Z;
          A -> B every 10s 0 times;
          A -> B cron [*/0 * * * *];
        }
        """);
    
    assertFalse(parser.hasErrors());
    assertTrue(result.isPresent());
    Log.getFindings().clear();
    
    StatechartWithTimerCoCoChecker checker = new StatechartWithTimerCoCoChecker();
    checker.addChecker(TimeTriggerConditionsCoCos.createChecker());
    checker.checkAll(result.get());
    
    assertHasFinding(AfterConditionHasValidDuration.NON_POSITIVE_DURATION);
    assertHasFinding(AfterConditionHasValidPeriod.NON_POSITIVE_PERIOD);
    assertHasFinding(OnConditionHasValidValue.INVALID_DATE_TIME);
    assertHasFinding(EveryTimeConditionHasValidRepetitions.NON_POSITIVE_REPETITIONS);
    assertHasFinding(CronExpressionIsValid.INVALID_STEP);
  }
  
  protected void assertHasFinding(String errorCode) {
    assertTrue(Log.getFindings().stream().anyMatch(finding -> finding.getMsg().contains(errorCode)),
        errorCode);
  }
  
}
