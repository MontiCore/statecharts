/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._visitor.UMLStatechartsHandler;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class UMLStatechartsPrettyPrinter implements UMLStatechartsHandler {
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
        node.getArguments().accept(getTraverser());
      }
    }
  }
  
  UMLStatechartsTraverser traverser;
  
  @Override public UMLStatechartsTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(UMLStatechartsTraverser traverser) {
    this.traverser = traverser;
  }


  public IndentPrinter getPrinter() {
    return printer;
  }
}
