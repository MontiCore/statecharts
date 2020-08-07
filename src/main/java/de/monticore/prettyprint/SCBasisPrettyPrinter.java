package de.monticore.prettyprint;

import de.monticore.scbasis._ast.*;
import de.monticore.scbasis._visitor.SCBasisVisitor;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;

public class SCBasisPrettyPrinter implements SCBasisVisitor {
  private SCBasisVisitor realThis = this;
  protected IndentPrinter printer;

  public SCBasisPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;
  }

  @Override
  public void handle(ASTSCArtifact node) {
    if (node.isPresentPackage()) {
      getPrinter().print("package ");
      getPrinter().print(node.getPackage());
      getPrinter().println(";");
    }
    for (ASTMCImportStatement importStatement : node.getMCImportStatementsList()) {
      importStatement.accept(getRealThis());
    }
    node.getStatechart().accept(getRealThis());
  }

  @Override
  public void handle(ASTStatechart node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
    }
    getPrinter().print("statechart ");
    if (node.isPresentName()) {
      getPrinter().print(node.getName());
    }
    getPrinter().println("{");
    getPrinter().indent();
    for (ASTSCStatechartElement elem : node.getSCStatechartElementsList()) {
      elem.accept(getRealThis());
    }
    getPrinter().unindent();
    getPrinter().println("}");
  }

  @Override
  public void handle(ASTSCState node) {
    node.getSCModifier().accept(getRealThis());
    getPrinter().print(" state ");
    getPrinter().print(node.getName());
    node.getSCSBody().accept(getRealThis());
    getPrinter().println(";");
  }

  @Override
  public void handle(ASTSCModifier node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
    }
    if (node.isInitial()) {
      getPrinter().print(" initial ");
    }
    if (node.isFinal()) {
      getPrinter().print(" final ");
    }
  }

  @Override
  public void handle(ASTSCEmptyBody node) {
    // empty
  }

  @Override
  public void handle(ASTSCTransition node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
    }
    getPrinter().print(node.getSourceName());
    getPrinter().print(" -> ");
    getPrinter().print(node.getTargetName());
    node.getSCTBody().accept(getRealThis());
    getPrinter().println(";");
  }

  @Override
  public SCBasisVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCBasisVisitor realThis) {
    this.realThis = realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
