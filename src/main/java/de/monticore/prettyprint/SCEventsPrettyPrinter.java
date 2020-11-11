/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scevents._ast.ASTSCFuncEventDef;
import de.monticore.scevents._ast.ASTSCFuncEventParameter;
import de.monticore.scevents._visitor.SCEventsVisitor;

public class SCEventsPrettyPrinter
    implements SCEventsVisitor {
  private SCEventsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCEventsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  
  @Override public void handle(ASTSCFuncEventDef node) {
    getPrinter().print("event ");
    if(node.isPresentMCReturnType()) {
      node.getMCReturnType().accept(getRealThis());
    }
    getPrinter().print(" " + node.getName());
    getPrinter().print("(");
    String comma = "";
    for(ASTSCFuncEventParameter p : node.getParamList()) {
      getPrinter().print(comma);
      p.accept(getRealThis());
      comma = ", ";
    }
    getPrinter().println(");");
  }
  
  @Override public void handle(ASTSCFuncEventParameter node) {
    node.getMCType().accept(getRealThis());
    getPrinter().print(" " + node.getName());
  }
  
  @Override
  public SCEventsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCEventsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
