/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.sccompleteness._visitor.SCCompletenessHandler;
import de.monticore.sccompleteness._visitor.SCCompletenessTraverser;

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
