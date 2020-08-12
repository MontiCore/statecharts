/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.triggeredstatecharts._ast.ASTSCEmptyEvent;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsVisitor;

public class TriggeredStatechartsPrettyPrinter
    implements TriggeredStatechartsVisitor {
  private TriggeredStatechartsVisitor realThis = this;
  protected IndentPrinter printer;

  public TriggeredStatechartsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void visit(ASTSCEmptyEvent node) {
    // empty
  }

  @Override
  public TriggeredStatechartsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(TriggeredStatechartsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
