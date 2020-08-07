package de.monticore.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinterDelegator;
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

    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinterDelegator(printer));
    getMCCommonStatementsVisitor().get().setRealThis(getRealThis());

    setMCVarDeclarationStatementsVisitor(new MCVarDeclarationStatementsPrettyPrinter(printer));
  }

  public String prettyprint(ASTUMLStatechartsNode node) {
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
