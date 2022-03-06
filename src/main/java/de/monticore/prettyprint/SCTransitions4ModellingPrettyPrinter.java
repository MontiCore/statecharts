/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sctransitions4modelling._ast.ASTEventTransitionAction;
import de.monticore.sctransitions4modelling._visitor.SCTransitions4ModellingHandler;
import de.monticore.sctransitions4modelling._visitor.SCTransitions4ModellingTraverser;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class SCTransitions4ModellingPrettyPrinter implements SCTransitions4ModellingHandler {
  protected IndentPrinter printer;

  public SCTransitions4ModellingPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTEventTransitionAction node) {
    if (node.isPresentMCBlockStatement()) {
      node.getMCBlockStatement().accept(getTraverser());
    }
    if (node.isPresentPost()) {
      getPrinter().print("[");
      node.getPost().accept(getTraverser());
      getPrinter().print("]");
    }
  }
  
  SCTransitions4ModellingTraverser traverser;
  
  @Override public SCTransitions4ModellingTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCTransitions4ModellingTraverser traverser) {
    this.traverser = traverser;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
