/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sctransitions4modelling._ast.ASTEventTransitionAction;
import de.monticore.sctransitions4modelling._visitor.SCTransitions4ModellingVisitor;

public class SCTransitions4ModellingPrettyPrinter
    implements SCTransitions4ModellingVisitor {
  private SCTransitions4ModellingVisitor realThis = this;
  protected IndentPrinter printer;

  public SCTransitions4ModellingPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTEventTransitionAction node) {
    if (node.isPresentMCBlockStatement()) {
      node.getMCBlockStatement().accept(getRealThis());
    }
    if (node.isPresentPost()) {
      getPrinter().print("[");
      node.getPost().accept(getRealThis());
      getPrinter().print("]");
    }
  }

  @Override
  public SCTransitions4ModellingVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCTransitions4ModellingVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
