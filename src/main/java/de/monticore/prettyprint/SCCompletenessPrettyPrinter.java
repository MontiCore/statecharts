/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.sccompleteness._visitor.SCCompletenessHandler;
import de.monticore.sccompleteness._visitor.SCCompletenessTraverser;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class SCCompletenessPrettyPrinter implements SCCompletenessHandler {
  protected IndentPrinter printer;

  public SCCompletenessPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCCompleteness node) {
    if (node.isComplete()) {
      getPrinter().print(" (c) ");
    }
    else if (node.isIncomplete()) {
      getPrinter().print(" (...) ");
    }
  }
  
  SCCompletenessTraverser traverser;
  
  @Override public SCCompletenessTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCCompletenessTraverser traverser) {
    this.traverser = traverser;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
