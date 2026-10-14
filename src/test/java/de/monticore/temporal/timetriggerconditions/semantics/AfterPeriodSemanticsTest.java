/* (c) https://github.com/MontiCore/monticore */
package de.monticore.temporal.timetriggerconditions.semantics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;
import de.monticore.temporal.timetriggerconditions._ast.ASTAfterCondition;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static de.monticore.temporal.timetriggerconditions.semantics.AfterPeriodSemantics.DurationKind.CALENDAR_RELATIVE;
import static de.monticore.temporal.timetriggerconditions.semantics.AfterPeriodSemantics.DurationKind.FIXED;

public class AfterPeriodSemanticsTest {
  
  @BeforeEach
  public void setUp() {
    StatechartWithTimerMill.reset();
    StatechartWithTimerMill.init();
  }
  
  @ParameterizedTest(name = "fixed ISO period: {0}")
  @ValueSource(strings = { "P1W", "P2D", "PT1H", "PT5M", "PT10S", "P2DT3H4M5S", "P0Y2D", "P0M2D" })
  public void testFixedPeriods(String source) throws IOException {
    var period = parse(source).getPeriod();
    
    assertEquals(FIXED, AfterPeriodSemantics.classify(period));
    assertFalse(AfterPeriodSemantics.isCalendarRelative(period));
  }
  
  @ParameterizedTest(name = "calendar-relative ISO period: {0}")
  @ValueSource(strings = { "P1Y", "P2M", "P1Y2M", "P1Y2M3D", "P2MT3H" })
  public void testCalendarRelativePeriods(String source) throws IOException {
    var period = parse(source).getPeriod();
    
    assertEquals(CALENDAR_RELATIVE, AfterPeriodSemantics.classify(period));
    assertTrue(AfterPeriodSemantics.isCalendarRelative(period));
  }
  
  @Test
  public void testNullPeriodIsRejected() {
    assertThrows(NullPointerException.class, () -> AfterPeriodSemantics.classify(null));
  }
  
  @Test
  public void testUnknownPeriodImplementationIsRejected() {
    var period = (de.monticore.temporal.isotemporals._ast.ASTISOPeriod) Proxy.newProxyInstance(
        getClass().getClassLoader(), new Class<?>[] {
            de.monticore.temporal.isotemporals._ast.ASTISOPeriod.class }, (instance, method,
                arguments) -> null);
    
    assertThrows(IllegalArgumentException.class, () -> AfterPeriodSemantics.classify(period));
  }
  
  protected ASTAfterCondition parse(String source) throws IOException {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    var result = parser.parse_StringSCArtifact("""
        statechart AfterPeriodSemanticsTest {
          initial state A;
          state B;
          A -> B after %s;
        }
        """.formatted(source));
    
    assertFalse(parser.hasErrors(), source);
    assertTrue(result.isPresent(), source);
    
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
    result.orElseThrow().accept(traverser);
    
    assertEquals(1, conditions.size(), source);
    return conditions.get(0);
  }
  
}
