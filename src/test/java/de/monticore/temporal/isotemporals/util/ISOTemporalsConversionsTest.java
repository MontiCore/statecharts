/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.isotemporals.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import de.se_rwth.commons.logging.LogStub;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.temporal.timetriggerconditions._ast.ASTOnCondition;

public class ISOTemporalsConversionsTest {
  
  @BeforeEach
  public void setUp() {
    LogStub.init();
  }
  
  @ParameterizedTest(name = "{0} has offset {1} seconds")
  @CsvSource({ "2026-07-20T12:00:00Z, 0", "2026-07-20T12:00:00+05:45, 20700",
      "2026-07-20T12:00:00-05:30, -19800", "2026-07-20T12:00:00-00:30, -1800" })
  public void testDateTimeOffsetMinutes(String source, int expectedSeconds) throws IOException {
    ASTOnCondition condition = parse("on " + source);
    
    assertEquals(expectedSeconds, ISOTemporalsConversions.toOffsetDateTime(condition.getIsoDateTime())
        .getOffset().getTotalSeconds());
  }
  
  @ParameterizedTest
  @CsvSource({ "12:00:00Z, 0", "T12:00:00Z, 0", "12:00:00+05:45, 20700", "12:00:00-05:30, -19800" })
  public void testTimeOffsetMinutes(String source, int expectedSeconds) throws IOException {
    ASTOnCondition condition = parse("on " + source);
    
    assertEquals(ZoneOffset.ofTotalSeconds(expectedSeconds), ISOTemporalsConversions.toOffsetTime(
        condition.getTimeOnly()).getOffset());
  }
  
  @ParameterizedTest
  @CsvSource({ "2024-02-29, 2024-02-29", "2026-07-20, 2026-07-20" })
  public void testDateConversion(String source, LocalDate expected) throws IOException {
    ASTOnCondition condition = parse("on " + source);
    
    assertEquals(expected, ISOTemporalsConversions.toLocalDate(condition.getDateOnly()));
  }
  
  protected ASTOnCondition parse(String source) throws IOException {
    StatechartWithTimerMill.reset();
    StatechartWithTimerMill.init();
    var parser = StatechartWithTimerMill.parser();
    var result = parser.parse_StringOnCondition(source);
    assertFalse(parser.hasErrors(), source + ": " + de.se_rwth.commons.logging.Log.getFindings());
    assertTrue(result.isPresent(), source);
    return result.orElseThrow();
  }
  
}
