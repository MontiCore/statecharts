/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.triggeredstatecharts._ast.ASTSCEmptyEvent;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsVisitor2;

/**
 * This PrettyPrinter is designed to handle exactly the
 * nonterminals from the respective language component (grammar).
 * this ensures modularity by simply composing
 * the individual pretty printers (or subclasses thereof)
 *
 */
public class TriggeredStatechartsPrettyPrinter implements TriggeredStatechartsVisitor2 {
  protected IndentPrinter printer;

  public TriggeredStatechartsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void visit(ASTSCEmptyEvent node) {
    // empty
  }
  

  public IndentPrinter getPrinter() {
    return printer;
  }
}
