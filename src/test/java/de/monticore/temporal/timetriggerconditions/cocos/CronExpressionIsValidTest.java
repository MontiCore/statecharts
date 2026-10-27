/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.temporal.timetriggerconditions._cocos.CronExpressionIsValid;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.io.IOException;

public class CronExpressionIsValidTest extends AbstractTimerConditionCoCoTest {
  
  @ParameterizedTest(name = "supported Cron expression: {0}")
  @ValueSource(strings = { "* * * * *", "0 12 * * *", "0  12 * * *", "0 0 1 1 0", "59 23 31 12 6",
      "15 3 * * 1-5", "5-5 3 * * *", "0 0 1,15 * *", "*/5 * * * *", "0-23/2 * * * *", "1/3 * * * *",
      "5,10-20/5 * * * *", "0 0 * JAN MON", "0 0 * jan-mar mon-fri", "0 0 * JaN-mAr MoN-fRi",
      "0 0 * 1-MAR 1-FRI", "0 0 * JAN,MAR *", "0 0 * * SUN", "0 0 * * 0", "0 0 * * 7",
      "0 0 * * SUN-7", "0 0 * * 0-7", "*,5 * * * *", "*/61 * * * *" })
  public void testSupportedExpressionsAreValid(String expression) throws IOException {
    checkValid(cron(expression));
  }
  
  @ParameterizedTest(name = "invalid Cron value or range: {0}")
  @ValueSource(strings = { "60 12 * * *", "0-60 12 * * *", "0,60 12 * * *", "0 24 * * *",
      "0 12 0 * *", "0 12 32 * *", "0 12 * 0 *", "0 12 * 13 *", "0 12 * * 8", "0 12 * * 5-1",
      "0 12 * * FRI-MON", "0 12 * DEC-FEB *", "JAN 12 * * *", "0 MON * * *", "0 12 JAN * *",
      "0 12 * MON *", "0 12 * * JAN", "0 12 * FOO *", "0 12 * * *-5",
      "999999999999999999999999999 12 * * *" })
  public void testOutOfRangeValuesAreInvalid(String expression) throws IOException {
    checkInvalid(cron(expression), CronExpressionIsValid.INVALID_FIELD_VALUE);
  }
  
  @ParameterizedTest(name = "invalid Cron step: {0}")
  @ValueSource(strings = { "*/0 * * * *", "0-23/0 * * * *", "0 0 * JAN/0 *", "0 0 * * MON-FRI/0",
      "*/999999999999999999999999999 * * * *" })
  public void testZeroAndOverflowingStepsAreInvalid(String expression) throws IOException {
    checkInvalid(cron(expression), CronExpressionIsValid.INVALID_STEP);
  }
  
  @ParameterizedTest(name = "calendar-impossible Cron expression: {0}")
  @ValueSource(strings = { "0 0 30 2 *", "0 0 31 2 *", "0 0 31 APR *", "0 0 31 4,6,9,11 *",
      "0 0 31 2 */2", "0 0 31 2 *,MON" })
  public void testCalendarImpossibleExpressionsProduceWarning(String expression)
      throws IOException {
    checkWarning(cron(expression), CronExpressionIsValid.IMPOSSIBLE_DATE);
  }
  
  @ParameterizedTest(name = "calendar-possible Cron expression: {0}")
  @ValueSource(strings = { "0 0 29 2 *", "0 0 30 2,4 *", "0 0 31 2,3 *", "0 0 31 2 MON",
      "0 0 */31 2 MON", "0 0 15,* 2 *", "0 0 31,* 2 MON", "0 0 31 2 MON,*", "0 0 31 2 MON,*,FRI" })
  public void testCalendarPossibleExpressionsDoNotProduceWarning(String expression)
      throws IOException {
    checkValid(cron(expression));
  }
  
  protected String cron(String expression) {
    return "cron [" + expression + "]";
  }
  
  @Override
  protected TimeTriggerConditionsCoCoChecker checker() {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new CronExpressionIsValid());
    return checker;
  }
  
}
