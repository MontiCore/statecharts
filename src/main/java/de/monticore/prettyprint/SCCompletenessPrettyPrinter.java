/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.sccompleteness._visitor.SCCompletenessVisitor;

public class SCCompletenessPrettyPrinter
    implements SCCompletenessVisitor {
  private SCCompletenessVisitor realThis = this;
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

  @Override
  public SCCompletenessVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCCompletenessVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
