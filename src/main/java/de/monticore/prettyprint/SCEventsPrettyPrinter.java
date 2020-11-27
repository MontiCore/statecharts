/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scevents._ast.ASTSCFuncEventDef;
import de.monticore.scevents._ast.ASTSCFuncEventParameter;
import de.monticore.scevents._visitor.SCEventsHandler;
import de.monticore.scevents._visitor.SCEventsTraverser;

public class SCEventsPrettyPrinter implements SCEventsHandler {
  protected IndentPrinter printer;

  public SCEventsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  
  @Override public void handle(ASTSCFuncEventDef node) {
    getPrinter().print("event ");
    if(node.isPresentMCReturnType()) {
      node.getMCReturnType().accept(getTraverser());
    }
    getPrinter().print(" " + node.getName());
    getPrinter().print("(");
    String comma = "";
    for(ASTSCFuncEventParameter p : node.getParamList()) {
      getPrinter().print(comma);
      p.accept(getTraverser());
      comma = ", ";
    }
    getPrinter().println(");");
  }
  
  @Override public void handle(ASTSCFuncEventParameter node) {
    node.getMCType().accept(getTraverser());
    getPrinter().print(" " + node.getName());
  }
  
  SCEventsTraverser traverser;
  
  @Override public SCEventsTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCEventsTraverser traverser) {
    this.traverser = traverser;
  }
  

  public IndentPrinter getPrinter() {
    return printer;
  }
}
