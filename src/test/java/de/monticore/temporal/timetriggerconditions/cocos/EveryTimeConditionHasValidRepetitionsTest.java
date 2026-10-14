/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import de.se_rwth.commons.logging.Log;
import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.temporal.timetriggerconditions._cocos.EveryTimeConditionHasValidRepetitions;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EveryTimeConditionHasValidRepetitionsTest extends AbstractTimerConditionCoCoTest {
  
  @ParameterizedTest(name = "valid recurring condition: {0}")
  @ValueSource(strings = { "every 10s", "every 5min 1 times",
      "from 2026-07-20T12:00:00Z every 10s 3 times" })
  public void testValidEveryTimeConditionRepetitions(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "invalid interval: {0}")
  @ValueSource(strings = { "every 0s", "every 5m", "every 5ms/s",
      "every 999999999999999999999999999s" })
  public void testInvalidIntervalIsRejected(String input) throws IOException {
    checkInvalid(input, EveryTimeConditionHasValidRepetitions.INVALID_INTERVAL);
  }
  
  @ParameterizedTest(name = "invalid start: {0}")
  @ValueSource(strings = { "from 2026-07-20T12:00:00 every 10s" })
  public void testMissingStartOffsetIsRejected(String input) throws IOException {
    checkInvalid(input, EveryTimeConditionHasValidRepetitions.INVALID_START);
  }
  
  @ParameterizedTest(name = "invalid repetition count: {0}")
  @ValueSource(strings = { "every 10s 0 times", "every 10s 999999999999999999999999999 times" })
  public void testZeroRepetitionsIsInvalid(String input) throws IOException {
    checkInvalid(input, EveryTimeConditionHasValidRepetitions.NON_POSITIVE_REPETITIONS);
  }
  
  @ParameterizedTest(name = "repetition count rejected by the parser: {0}")
  @ValueSource(strings = { "every 10s -1 times", "every 10s 1.5 times" })
  public void testNonNaturalRepetitionCountsAreRejectedByParser(String input) throws IOException {
    var parser = StatechartWithTimerMill.parser();
    var result = parser.parse_StringSCArtifact("""
        statechart InvalidRepetitions {
          initial state A;
          state B;
          A -> B %s;
        }
        """.formatted(input));
    
    assertTrue(parser.hasErrors() || result.isEmpty(), input);
    Log.getFindings().clear();
  }
  
  @Override
  protected TimeTriggerConditionsCoCoChecker checker() {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new EveryTimeConditionHasValidRepetitions());
    return checker;
  }
  
}
