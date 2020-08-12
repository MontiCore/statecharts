/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor;

public class UMLStatechartsPrettyPrinter
    implements UMLStatechartsVisitor {
  private UMLStatechartsVisitor realThis = this;
  protected IndentPrinter printer;

  public UMLStatechartsPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCUMLEvent node) {
    if (node.isPresentName()) {
      getPrinter().print(node.getName());
      getPrinter().print(" ");
      if (node.isPresentArguments()) {
        node.getArguments().accept(getRealThis());
      }
    }
  }

  @Override
  public UMLStatechartsVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(UMLStatechartsVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
