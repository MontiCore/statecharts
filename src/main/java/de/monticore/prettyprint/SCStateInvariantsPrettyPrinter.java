package de.monticore.prettyprint;

import de.monticore.scstateinvariants._ast.ASTSCStateInvariant;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsVisitor;

public class SCStateInvariantsPrettyPrinter
    implements SCStateInvariantsVisitor {
  private SCStateInvariantsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCStateInvariantsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCStateInvariant node) {
    getPrinter().print(" [");
    node.getExpression().accept(getRealThis());
    getPrinter().print("] ");
  }

  @Override
  public SCStateInvariantsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCStateInvariantsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
