package de.monticore.prettyprint;

import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.scactions._ast.ASTSCExitAction;
import de.monticore.scactions._visitor.SCActionsVisitor;

public class SCActionsPrettyPrinter
    implements SCActionsVisitor {
  private SCActionsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCActionsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCEntryAction node) {
    getPrinter().print("entry ");
    node.getSCABody().accept(getRealThis());
  }

  @Override
  public void handle(ASTSCExitAction node) {
    getPrinter().print("accept ");
    node.getSCABody().accept(getRealThis());
  }

  @Override
  public SCActionsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCActionsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
