/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsVisitor;

public class SCStateInvariantsPrettyPrinter
    implements SCStateInvariantsVisitor {
  private SCStateInvariantsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCStateInvariantsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCInvState node) {
    node.getSCModifier().accept(getRealThis());
    getPrinter().print(" state " + node.getName());
    getPrinter().print(" [");
    node.getExpression().accept(getRealThis());
    getPrinter().print("] ");
    node.getSCSBody().accept(getRealThis());
    getPrinter().print(";");
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
