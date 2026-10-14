/* (c) https://github.com/MontiCore/monticore */
package de.monticore.statechartwithtimer.integration;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.Test;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._ast.ASTTimerEvent;
import de.monticore.statechartwithtimer.lang.AbstractTest;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronCondition;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronName;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronRange;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronValue;
import de.monticore.temporal.timetriggerconditions._ast.ASTCronWildcard;
import de.monticore.temporal.timetriggerconditions._cocos.CronExpressionIsValid;
import de.monticore.temporal.timetriggerconditions._cocos.TimeTriggerConditionsCoCoChecker;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CronStatechartIntegrationTest extends AbstractTest {
  
  protected static final String MODEL =
      "src/test/resources/statechartwithtimer/parser/CronStatechart.sc";
  
  @Test
  public void testCronStatechartEndToEnd() {
    ASTSCArtifact statechart = parseModel(MODEL);
    List<ASTCronCondition> conditions = findCronConditions(statechart);
    
    assertEquals(1, conditions.size());
    var expression = conditions.get(0).getExpression();
    ASTCronValue minute = assertInstanceOf(ASTCronValue.class, expression.getMinute().getElements(
        0));
    ASTCronRange dayOfWeek = assertInstanceOf(ASTCronRange.class, expression.getDayOfWeek()
        .getElements(0));
    assertInstanceOf(ASTCronWildcard.class, minute.getValue());
    assertEquals(15, minute.getStep().getValue());
    assertEquals("MON", assertInstanceOf(ASTCronName.class, dayOfWeek.getStart()).getValue());
    assertEquals("FRI", assertInstanceOf(ASTCronName.class, dayOfWeek.getEnd()).getValue());
    
    TimeTriggerConditionsCoCoChecker checker = new TimeTriggerConditionsCoCoChecker();
    checker.addCoCo(new CronExpressionIsValid());
    checker.checkAll(conditions.get(0));
    
    assertTrue(Log.getFindings().isEmpty());
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
  
}
