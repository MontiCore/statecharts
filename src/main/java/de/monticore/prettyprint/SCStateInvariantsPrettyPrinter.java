/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsHandler;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsTraverser;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class SCStateInvariantsPrettyPrinter implements SCStateInvariantsHandler {
  protected IndentPrinter printer;

  public SCStateInvariantsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCInvState node) {
    node.getSCModifier().accept(getTraverser());
    getPrinter().print(" state " + node.getName());
    getPrinter().print(" [");
    node.getExpression().accept(getTraverser());
    getPrinter().print("] ");
    node.getSCSBody().accept(getTraverser());
    getPrinter().print(";");
  }
  
  SCStateInvariantsTraverser traverser;
  
  @Override public SCStateInvariantsTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCStateInvariantsTraverser traverser) {
    this.traverser = traverser;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
