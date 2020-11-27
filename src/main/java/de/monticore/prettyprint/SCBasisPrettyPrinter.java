/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.*;
import de.monticore.scbasis._visitor.SCBasisHandler;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;

public class SCBasisPrettyPrinter implements SCBasisHandler {
  protected IndentPrinter printer;
  
  protected SCBasisTraverser traverser;
  
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
    for (ASTMCImportStatement importStatement : node.getMCImportStatementList()) {
      importStatement.accept(getTraverser());
    }
    node.getStatechart().accept(getTraverser());
  }

  @Override
  public void handle(ASTNamedStatechart node) {
    printStatechartHead(node);
    getPrinter().print(node.getName());
    printStatechartBody(node);
  }
  
 
  
  @Override
  public void handle(ASTUnnamedStatechart node) {
    printStatechartHead(node);
    printStatechartBody(node);
  }
  
  protected void printStatechartBody(ASTStatechart node) {
    getPrinter().println("{");
    getPrinter().indent();
    for (ASTSCStatechartElement elem : node.getSCStatechartElementList()) {
      elem.accept(getTraverser());
    }
    getPrinter().unindent();
    getPrinter().println("}");
  }
  
  protected void printStatechartHead(ASTStatechart node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getTraverser());
    }
    getPrinter().print("statechart ");
  }
  
  @Override
  public void handle(ASTSCState node) {
    node.getSCModifier().accept(getTraverser());
    getPrinter().print(" state ");
    getPrinter().print(node.getName());
    node.getSCSBody().accept(getTraverser());
    getPrinter().println(";");
  }

  @Override
  public void handle(ASTSCModifier node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getTraverser());
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
      node.getStereotype().accept(getTraverser());
    }
    getPrinter().print(node.getSourceName());
    getPrinter().print(" -> ");
    getPrinter().print(node.getTargetName());
    getPrinter().print(" ");
    node.getSCTBody().accept(getTraverser());
    getPrinter().println(";");
  }

  @Override
  public SCBasisTraverser getTraverser() {
    return traverser;
  }

  @Override
  public void setTraverser(SCBasisTraverser traverser) {
    this.traverser = traverser;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }
}
