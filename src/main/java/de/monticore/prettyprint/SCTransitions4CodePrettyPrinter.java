/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor;

public class SCTransitions4CodePrettyPrinter
    implements SCTransitions4CodeVisitor {
  private SCTransitions4CodeVisitor realThis = this;
  protected IndentPrinter printer;

  public SCTransitions4CodePrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTTransitionBody node) {
    if (node.isPresentPre()) {
      getPrinter().print("[");
      node.getPre().accept(getRealThis());
      getPrinter().print("]");
    }
    if (node.isPresentSCEvent()) {
      node.getSCEvent().accept(getRealThis());
    }
    if (node.isPresentTransitionAction()) {
      getPrinter().print(" / ");
      node.getTransitionAction().accept(getRealThis());
    }
  }

  @Override
  public SCTransitions4CodeVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCTransitions4CodeVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
