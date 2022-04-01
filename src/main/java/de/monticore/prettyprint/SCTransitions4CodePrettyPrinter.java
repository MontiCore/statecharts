/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sctransitions4code._ast.ASTAnteAction;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeHandler;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeTraverser;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class SCTransitions4CodePrettyPrinter implements SCTransitions4CodeHandler {
  protected IndentPrinter printer;

  public SCTransitions4CodePrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTTransitionBody node) {
    if (node.isPresentPre()) {
      getPrinter().print("[");
      node.getPre().accept(getTraverser());
      getPrinter().print("]");
    }
    if (node.isPresentSCEvent()) {
      node.getSCEvent().accept(getTraverser());
    }
    if (node.isPresentTransitionAction()) {
      getPrinter().print(" / ");
      node.getTransitionAction().accept(getTraverser());
    }
  }

  @Override
  public void handle(ASTAnteAction node) {
    getPrinter().print(" { ");
    node.getMCBlockStatement().accept(this.getTraverser());
    getPrinter().print(" }");
  }
  
  SCTransitions4CodeTraverser traverser;
  
  @Override public SCTransitions4CodeTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCTransitions4CodeTraverser traverser) {
    this.traverser = traverser;
  }


  public IndentPrinter getPrinter() {
    return printer;
  }
}
