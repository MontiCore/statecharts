/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scdoactions._ast.ASTSCDoAction;
import de.monticore.scdoactions._visitor.SCDoActionsHandler;
import de.monticore.scdoactions._visitor.SCDoActionsTraverser;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class SCDoActionsPrettyPrinter implements SCDoActionsHandler {
  protected IndentPrinter printer;

  public SCDoActionsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCDoAction node) {
    getPrinter().print("do / ");
    node.getSCABody().accept(getTraverser());
  }
  
  SCDoActionsTraverser traverser;
  
  @Override public SCDoActionsTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCDoActionsTraverser traverser) {
    this.traverser = traverser;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
