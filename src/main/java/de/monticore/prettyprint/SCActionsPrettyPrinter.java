/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scactions._ast.ASTSCEntryAction;
import de.monticore.scactions._ast.ASTSCExitAction;
import de.monticore.scactions._visitor.SCActionsHandler;
import de.monticore.scactions._visitor.SCActionsTraverser;

public class SCActionsPrettyPrinter
    implements SCActionsHandler {
  protected IndentPrinter printer;

  public SCActionsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCEntryAction node) {
    getPrinter().print("entry / ");
    node.getSCABody().accept(getTraverser());
  }

  @Override
  public void handle(ASTSCExitAction node) {
    getPrinter().print("exit / ");
    node.getSCABody().accept(getTraverser());
  }
  
  SCActionsTraverser traverser;
  
  @Override public SCActionsTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCActionsTraverser traverser) {
    this.traverser = traverser;
  }
  
  public IndentPrinter getPrinter() {
    return printer;
  }
}
