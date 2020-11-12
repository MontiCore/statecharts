/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.sctransitions4modelling._ast.ASTSCTransitions4ModellingNode;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCReturnStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCVarDeclarationStatementsPrettyPrinter;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;
import de.monticore.umlstatecharts._ast.ASTUMLStatechartsNode;
import de.monticore.umlstatecharts._visitor.UMLStatechartsDelegatorVisitor;

public class UMLStatechartsPrettyPrinterDelegator
    extends UMLStatechartsDelegatorVisitor {
  protected UMLStatechartsDelegatorVisitor realThis = this;
  protected IndentPrinter printer;

  public UMLStatechartsPrettyPrinterDelegator() {
    this(new IndentPrinter());
  }

  public UMLStatechartsPrettyPrinterDelegator(IndentPrinter printer) {
    this.printer = printer;

    // UMLStereotype, MCBasicTypes, ExpressionsBasis
    setUMLStereotypeVisitor(new UMLStereotypePrettyPrinter(printer));
    setUMLStatechartsVisitor(new UMLStatechartsPrettyPrinter(printer));
    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
    setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));
    setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));

    setSCBasisVisitor(new SCBasisPrettyPrinter(printer));

    setSCActionsVisitor(new SCActionsPrettyPrinter(printer));
    setSCDoActionsVisitor(new SCDoActionsPrettyPrinter(printer));
    setSCStateHierarchyVisitor(new SCStateHierarchyPrettyPrinter(printer));
    setSCStateInvariantsVisitor(new SCStateInvariantsPrettyPrinter(printer));
    setSCCompletenessVisitor(new SCCompletenessPrettyPrinter(printer));
    setSCTransitions4ModellingVisitor(new SCTransitions4ModellingPrettyPrinter(printer));
    setSCTransitions4CodeVisitor(new SCTransitions4CodePrettyPrinter(printer));
    setSCEventsVisitor(new SCEventsPrettyPrinter(printer));

    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
    setMCReturnStatementsVisitor(new MCReturnStatementsPrettyPrinter(printer));

    setMCVarDeclarationStatementsVisitor(new MCVarDeclarationStatementsPrettyPrinter(printer));
  }

  public String prettyprint(ASTUMLStatechartsNode node) {
    this.getPrinter().clearBuffer();
    node.accept(this.getRealThis());
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCTransitions4ModellingNode node) {
    this.getPrinter().clearBuffer();
    node.accept(this.getRealThis());
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCBasisNode node) {
    this.getPrinter().clearBuffer();
    node.accept(this.getRealThis());
    return this.getPrinter().getContent();
  }

  public UMLStatechartsDelegatorVisitor getRealThis() {
    return this.realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }

}
