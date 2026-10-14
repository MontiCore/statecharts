/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.cocos;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.temporal.timetriggerconditions._cocos.AfterConditionHasValidPeriod;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.io.IOException;

public class AfterConditionHasValidPeriodTest extends AbstractTimerConditionCoCoTest {
  
  @ParameterizedTest(name = "positive ISO period: {0}")
  @ValueSource(strings = { "after P1Y", "after P2M", "after P1Y2M3DT4H5M6S", "after P2D",
      "after PT1H", "after PT5M", "after PT10S", "after P1W", "after PT0.5H", "after PT0.5M",
      "after PT0.5S", "after PT0.000001S" })
  public void testPositiveIsoPeriodsAreValidInStatecharts(String input) throws IOException {
    checkValid(input);
  }
  
  @ParameterizedTest(name = "non-positive ISO period: {0}")
  @ValueSource(strings = { "after P", "after PT", "after P0Y", "after P0M", "after P0D",
      "after P0W", "after PT0H", "after PT0M", "after PT0S", "after PT0.0H", "after PT0.0M",
      "after PT0.0S", "after P0Y0M0DT0H0M0.000S" })
  public void testZeroIsoPeriodsAreInvalidInStatecharts(String input) throws IOException {
    checkInvalid(input, AfterConditionHasValidPeriod.NON_POSITIVE_PERIOD);
  }
  
  @Override
  protected TimeTriggerConditionsCoCoChecker checker() {
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new AfterConditionHasValidPeriod());
    return checker;
  }
  
}
