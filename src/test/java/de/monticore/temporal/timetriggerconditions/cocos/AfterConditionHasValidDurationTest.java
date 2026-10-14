/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.temporal.timetriggerconditions._cocos.AfterConditionHasValidDuration;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.io.IOException;

public class AfterConditionHasValidDurationTest extends AbstractTimerConditionCoCoTest {
  
  @ParameterizedTest(name = "supported duration: {0}")
  @ValueSource(strings = { "after 1ms", "after 1s", "after 1min", "after 1h", "after 1d",
      "after 0.001s", "after 1.5s", "after 1L s", "after 1.5F s" })
  public void testSupportedDurationsAreValid(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "non-positive duration: {0}")
  @ValueSource(strings = { "after 0ms", "after 0s", "after 0.0min", "after 0L h", "after 0.0F d" })
  public void testZeroDurationIsInvalid(String input) throws IOException {
    checkInvalid(input, AfterConditionHasValidDuration.NON_POSITIVE_DURATION);
  }
  
  @ParameterizedTest(name = "unsupported duration unit: {0}")
  @ValueSource(strings = { "after 10m", "after 3kg", "after 1A", "after 1K", "after 1m/s",
      "after 1s^2" })
  public void testUnsupportedUnitsAreInvalid(String input) throws IOException {
    checkInvalid(input, AfterConditionHasValidDuration.UNSUPPORTED_UNIT);
  }
  
  @ParameterizedTest(name = "unrepresentable duration: {0}")
  @ValueSource(strings = { "after 999999999999999999999999999s",
      "after 999999999999999999999999999L s" })
  public void testOversizedNumericLiteralIsInvalid(String input) throws IOException {
    checkInvalid(input, AfterConditionHasValidDuration.INVALID_NUMERIC_LITERAL);
  }
  
  @Override
  protected TimeTriggerConditionsCoCoChecker checker() {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new AfterConditionHasValidDuration());
    return checker;
  }
  
}
