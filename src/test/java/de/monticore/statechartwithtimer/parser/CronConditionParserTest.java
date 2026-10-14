/* (c) https://github.com/MontiCore/monticore */
package de.monticore.statechartwithtimer.parser;

import de.monticore.scbasis._ast.ASTSCArtifact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;
import de.monticore.statechartwithtimer.lang.AbstractTest;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronCondition;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronName;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronNumber;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronRange;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronValue;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronWildcard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CronConditionParserTest extends AbstractTest {
  
  @ParameterizedTest(name = "supported Cron expression: {0}")
  @ValueSource(strings = { "0 12 * * *", "15 3 * * 1-5", "0 0 1,15 * *", "*/5 * * * *",
      "5,10-20/5 * * * *", "0 0 * JAN MON", "0 0 * JAN-MAR/2 MON-FRI", "0 0 * 1-MAR 1-FRI",
      "0 0 * * 7", "0 0 * * mon", "*,5 * * * *" })
  public void testStructuredCronExpressionsParse(String expression) {
    ASTSCArtifact statechart = parseStringModel(statechartWith("cron [" + expression + "]"));
    List<ASTCronCondition> conditions = findCronConditions(statechart);
    
    assertEquals(1, conditions.size(), expression);
  }
  
  @Test
  public void testCronExpressionHasStructuredFields() {
    ASTSCArtifact statechart = parseStringModel(statechartWith(
        "cron [*/15 3 * JAN-MAR MON-FRI/2]"));
    var expression = findCronConditions(statechart).get(0).getExpression();
    
    ASTCronValue minute = assertInstanceOf(ASTCronValue.class, expression.getMinute().getElements(
        0));
    ASTCronValue hour = assertInstanceOf(ASTCronValue.class, expression.getHour().getElements(0));
    ASTCronRange month = assertInstanceOf(ASTCronRange.class, expression.getMonth().getElements(0));
    ASTCronRange dayOfWeek = assertInstanceOf(ASTCronRange.class, expression.getDayOfWeek()
        .getElements(0));
    
    assertInstanceOf(ASTCronWildcard.class, minute.getValue());
    assertTrue(minute.isPresentStep());
    assertEquals(15, minute.getStep().getValue());
    assertEquals(3, assertInstanceOf(ASTCronNumber.class, hour.getValue()).getValue().getValue());
    assertInstanceOf(ASTCronWildcard.class, assertInstanceOf(ASTCronValue.class, expression
        .getDayOfMonth().getElements(0)).getValue());
    assertEquals("JAN", assertInstanceOf(ASTCronName.class, month.getStart()).getValue());
    assertEquals("MAR", assertInstanceOf(ASTCronName.class, month.getEnd()).getValue());
    assertEquals("MON", assertInstanceOf(ASTCronName.class, dayOfWeek.getStart()).getValue());
    assertEquals("FRI", assertInstanceOf(ASTCronName.class, dayOfWeek.getEnd()).getValue());
    assertTrue(dayOfWeek.isPresentStep());
    assertEquals(2, dayOfWeek.getStep().getValue());
  }
  
  @ParameterizedTest(name = "malformed Cron syntax: {0}")
  @ValueSource(strings = { "cron", "cron \"0 12 * * *\"", "cron 0 12 * * *", "cron []",
      "cron [0 12 * *]", "cron [0 0 12 * * *]", "cron [@daily]", "cron [0 12 * * ?]",
      "cron [0 12 * * MON#2]", "cron [0 12 1,,15 * *]" })
  public void testMalformedCronExpressionsDoNotParse(String event) throws IOException {
    assertDoesNotParse(event);
  }
  
  protected void assertDoesNotParse(String event) throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    parser.parse_StringSCArtifact(statechartWith(event));
    
    assertTrue(parser.hasErrors(), event);
  }
  
  protected List<ASTCronCondition> findCronConditions(ASTSCArtifact statechart) {
    List<ASTCronCondition> conditions = new ArrayList<>();
    var traverser = StatechartWithTimerMill.inheritanceTraverser();
    traverser.add4StatechartWithTimer(
        new de.monticore.statechartwithtimer._visitor.StatechartWithTimerVisitor2() {
          
          @Override
          public void visit(ASTTimerEvent node) {
            if (node.getTimerCondition() instanceof ASTCronCondition) {
              conditions.add((ASTCronCondition) node.getTimerCondition());
            }
          }
          
        });
    statechart.accept(traverser);
    return conditions;
  }
  
  protected String statechartWith(String event) {
    return """
        statechart CronParserTest {
          initial state A;
          state B;
          A -> B %s;
        }
        """.formatted(event);
  }
  
}
