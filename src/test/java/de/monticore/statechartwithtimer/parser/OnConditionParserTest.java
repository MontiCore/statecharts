/* (c) https://github.com/MontiCore/monticore */
package de.monticore.statechartwithtimer.parser;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.temporal.isotemporals._ast.ASTISODateTime;
import de.se_rwth.commons.logging.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;
import de.monticore.statechartwithtimer.lang.AbstractTest;
import de.monticore.temporal.timetriggerconditions._ast.ASTOnCondition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static de.monticore.temporal.isotemporals.util.ISOTemporalsConversions.toOffsetDateTime;

public class OnConditionParserTest extends AbstractTest {
  
  @ParameterizedTest(name = "structured ISO date-time: {0}")
  @ValueSource(strings = { "2026-07-20T12:00:00Z", "2026-07-20T12:00Z",
      "2026-07-20T12:00:00.500Z" })
  public void testOnConditionsParseDateTimesAsStructuredIsoValues(String dateTime) {
    ASTSCArtifact statechart = parseStringModel(statechartWith("on " + dateTime));
    List<ASTOnCondition> conditions = findConditions(statechart);
    
    assertEquals(1, conditions.size(), dateTime);
    ASTISODateTime value = conditions.getFirst().getIsoDateTime();
    assertEquals(dateTime, value.toRawString());
    assertTrue(Log.getFindings().isEmpty(), Log.getFindings().toString());
  }
  
  @Test
  public void testNumericOffsetsRetainMinutePrecisionInStructuredAst() {
    var expectedOffsets = java.util.Map.of("2026-07-20T12:00:00+02:00", 120,
        "2026-07-20T12:00:00-05:30", -330, "2026-07-20T12:00:00+05:45", 345,
        "2026-07-20T12:00:00-00:30", -30);
    
    expectedOffsets.forEach((dateTime, expectedMinutes) -> {
      ASTSCArtifact statechart = parseStringModel(statechartWith("on " + dateTime));
      ASTISODateTime value = findConditions(statechart).getFirst().getIsoDateTime();
      
      assertEquals(expectedMinutes / 60, value.getTime().getTimeShiftHour(), dateTime);
      assertEquals(Math.abs(expectedMinutes) % 60, value.getTime().getTimeShiftMinute(), dateTime);
      assertEquals(expectedMinutes * 60, value.getTime().getLong(
          java.time.temporal.ChronoField.OFFSET_SECONDS), dateTime);
      assertEquals(expectedMinutes * 60, toOffsetDateTime(value).getOffset().getTotalSeconds(),
          dateTime);
      assertTrue(Log.getFindings().isEmpty(), Log.getFindings().toString());
    });
  }
  
  @Test
  public void testOnConditionResourceParses() throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    var result = parser.parse("src/test/resources/statechartwithtimer/parser/OnStatechart.sc");
    
    assertFalse(parser.hasErrors());
    assertTrue(result.isPresent());
    assertEquals(1, findConditions(result.get()).size());
  }
  
  @ParameterizedTest(name = "malformed on condition: {0}")
  @ValueSource(strings = { "on", "on tomorrow" })
  public void testMalformedOnConditionsDoNotParse(String event) throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    parser.parse_StringSCArtifact(statechartWith(event));
    
    assertTrue(parser.hasErrors(), event);
  }
  
  protected List<ASTOnCondition> findConditions(ASTSCArtifact statechart) {
    List<ASTOnCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTOnCondition) {
              conditions.add(assertInstanceOf(ASTOnCondition.class, node.getTimerCondition()));
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected String statechartWith(String event) {
    return """
        statechart OnConditionParserTest {
          initial state A;
          state B;
          A -> B %s;
        }
        """.formatted(event);
  }
  
}
