/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.scactions._ast.ASTSCExitAction;
import de.monticore.scactions._visitor.SCActionsVisitor;
import de.monticore.scevents._ast.ASTSCEventDef;
import de.monticore.scevents._ast.ASTSCEventParameter;
import de.monticore.scevents._visitor.SCEventsVisitor;

public class SCEventsPrettyPrinter
    implements SCEventsVisitor {
  private SCEventsVisitor realThis = this;
  protected IndentPrinter printer;

  public SCEventsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override public void handle(ASTSCEventDef node) {
    getPrinter().print("event ");
    node.getMCReturnType().accept(getRealThis());
    getPrinter().print(" " + node.getName());
    getPrinter().print("(");
    String comma = "";
    for(ASTSCEventParameter p : node.getSCEventParameterList()) {
      getPrinter().print(comma);
      p.accept(getRealThis());
      comma = ", ";
    }
    getPrinter().println(")");
  }
  
  @Override public void handle(ASTSCEventParameter node) {
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
