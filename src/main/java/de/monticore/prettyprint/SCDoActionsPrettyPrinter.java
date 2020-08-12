/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scdoactions._ast.ASTSCDoAction;
import de.monticore.scdoactions._visitor.SCDoActionsVisitor;

public class SCDoActionsPrettyPrinter
    implements SCDoActionsVisitor {
  private SCDoActionsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCDoActionsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCDoAction node) {
    getPrinter().print("do ");
    node.getSCABody().accept(getRealThis());
  }

  @Override
  public SCDoActionsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCDoActionsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
