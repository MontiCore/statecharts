/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.ASTSCStateElement;
import de.monticore.scstatehierarchy._ast.ASTSCHierarchyBody;
import de.monticore.scstatehierarchy._ast.ASTSCInternTransition;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyHandler;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyTraverser;

public class SCStateHierarchyPrettyPrinter implements SCStateHierarchyHandler {
  protected IndentPrinter printer;

  public SCStateHierarchyPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCHierarchyBody node) {
    getPrinter().println("{");
    getPrinter().indent();
    for (ASTSCStateElement element : node.getSCStateElementList()){
      element.accept(getTraverser());
    }
    getPrinter().unindent();
    getPrinter().println("}");
  }

  @Override
  public void handle(ASTSCInternTransition node) {
    if (node.isPresentStereotype()){
      node.getStereotype().accept(getTraverser());
    }
    getPrinter().print(" -> ");
    node.getSCTBody().accept(getTraverser());
    getPrinter().println(";");
  }
  
  SCStateHierarchyTraverser traverser;
  
  @Override public SCStateHierarchyTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCStateHierarchyTraverser traverser) {
    this.traverser = traverser;
  }
  

  public IndentPrinter getPrinter() {
    return printer;
  }
}
