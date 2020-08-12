/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.ASTSCStateElement;
import de.monticore.scstatehierarchy._ast.ASTSCHierarchyBody;
import de.monticore.scstatehierarchy._ast.ASTSCInternTransition;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyVisitor;

public class SCStateHierarchyPrettyPrinter
    implements SCStateHierarchyVisitor {
  private SCStateHierarchyVisitor realThis = this;
  protected IndentPrinter printer;

  public SCStateHierarchyPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCHierarchyBody node) {
    getPrinter().println("{");
    getPrinter().indent();
    for (ASTSCStateElement element : node.getSCStateElementsList()){
      element.accept(getRealThis());
    }
    getPrinter().unindent();
    getPrinter().println("}");
  }

  @Override
  public void handle(ASTSCInternTransition node) {
    if (node.isPresentStereotype()){
      node.getStereotype().accept(getRealThis());
    }
    getPrinter().print(" -> ");
    node.getSCTBody().accept(getRealThis());
    getPrinter().println(";");
  }

  @Override
  public SCStateHierarchyVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCStateHierarchyVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
