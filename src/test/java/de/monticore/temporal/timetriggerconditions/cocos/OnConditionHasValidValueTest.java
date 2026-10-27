/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import java.io.IOException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.temporal.timetriggerconditions._cocos.OnConditionHasValidValue;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

public class OnConditionHasValidValueTest extends AbstractTimerConditionCoCoTest {
  
  @ParameterizedTest(name = "valid date-time: {0}")
  @ValueSource(strings = { "on 2026-07-20T12:00:00Z", "on 2026-07-20T12:00Z",
      "on 2026-07-20T12:00:00.1Z", "on 2026-07-20T12:00:00.12Z", "on 2026-07-20T12:00:00.123Z",
      "on 2026-07-20T12:00:00.1234Z", "on 2026-07-20T12:00:00.12345Z",
      "on 2026-07-20T12:00:00.123456Z", "on 2026-07-20T12:00:00.1234567Z",
      "on 2026-07-20T12:00:00.12345678Z", "on 2026-07-20T12:00:00.123456789Z",
      "on 2026-07-20T12:00:00+05:45", "on 2026-07-20T12:00:00-05:30",
      "on 2026-07-20T12:00:00-00:30", "on 2026-07-20T12:00:00+18:00", "on 2024-02-29T00:00:00Z",
      "on 0001-01-01T00:00:00Z", "on 9999-12-31T23:59:59Z" })
  public void testDateTimesAreAccepted(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "invalid date-time: {0}")
  @ValueSource(strings = { "on 2026-13-01T12:00:00Z", "on 2026-07-32T12:00:00Z",
      "on 2025-02-29T12:00:00Z", "on 2026-07-20T24:00:00Z", "on 2026-07-20T12:60:00Z",
      "on 2026-07-20T12:00:60Z", "on 2026-07-20T12:00:00", "on 2026-07-20T12:00:00.1234567890Z",
      "on 2026-07-20T12:00:00+18:01", "on 2026-07-20T12:00:00-18:01",
      "on 2026-07-20T12:00:00+02:60" })
  public void testDateTimesAreRejected(String input) throws IOException {
    checkInvalid(input, OnConditionHasValidValue.INVALID_DATE_TIME);
  }
  
  @ParameterizedTest(name = "valid date: {0}")
  @ValueSource(strings = { "on 2026-07-20", "on 2024-02-29" })
  public void testDatesAreAccepted(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "invalid date: {0}")
  @ValueSource(strings = { "on 2026-13-01", "on 2026-07-32", "on 2025-02-29" })
  public void testDatesAreRejected(String input) throws IOException {
    checkInvalid(input, OnConditionHasValidValue.INVALID_DATE);
  }
  
  @ParameterizedTest(name = "valid time: {0}")
  @ValueSource(strings = { "on 12:00Z", "on 12:00:00+00:00", "on 12:00:00+05:45",
      "on 12:00:00-05:30", "on 12:00:00-00:30", "on 12:00:00+18:00", "on 12:00:00.1Z",
      "on 12:00:00.12Z", "on 12:00:00.123Z", "on 12:00:00.1234Z", "on 12:00:00.12345Z",
      "on 12:00:00.123456Z", "on 12:00:00.1234567Z", "on 12:00:00.12345678Z",
      "on 12:00:00.123456789Z" })
  public void testTimesAreAccepted(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "invalid time: {0}")
  @ValueSource(strings = { "on 12:00", "on 12:00:00", "on 24:00:00Z", "on 12:60:00Z",
      "on 12:00:60Z", "on 12:00:00.1234567890Z", "on 12:00:00+18:01", "on 12:00:00-18:01",
      "on 12:00:00+02:60" })
  public void testTimesAreRejected(String input) throws IOException {
    checkInvalid(input, OnConditionHasValidValue.INVALID_TIME);
  }
  
  @Override
  protected TimeTriggerConditionsCoCoChecker checker() {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new OnConditionHasValidValue());
    return checker;
  }
  
}
