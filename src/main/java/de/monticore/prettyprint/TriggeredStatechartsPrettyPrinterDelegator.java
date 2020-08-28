/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCVarDeclarationStatementsPrettyPrinter;
import de.monticore.triggeredstatecharts._ast.ASTTriggeredStatechartsNode;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsDelegatorVisitor;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;
import de.monticore.umlstatecharts._ast.ASTUMLStatechartsNode;
import de.monticore.umlstatecharts._visitor.UMLStatechartsDelegatorVisitor;

public class TriggeredStatechartsPrettyPrinterDelegator
    extends TriggeredStatechartsDelegatorVisitor {
  protected TriggeredStatechartsDelegatorVisitor realThis = this;
  protected IndentPrinter printer;

  public TriggeredStatechartsPrettyPrinterDelegator() {
    this(new IndentPrinter());
  }

  public TriggeredStatechartsPrettyPrinterDelegator(IndentPrinter printer) {
    this.printer = printer;

    // UMLStereotype, MCBasicTypes, ExpressionsBasis
    setUMLStereotypeVisitor(new UMLStereotypePrettyPrinter(printer));
    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
    setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));
    setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));

    setSCBasisVisitor(new SCBasisPrettyPrinter(printer));

    setSCActionsVisitor(new SCActionsPrettyPrinter(printer));
    setSCStateHierarchyVisitor(new SCStateHierarchyPrettyPrinter(printer));
    setSCTransitions4CodeVisitor(new SCTransitions4CodePrettyPrinter(printer));

    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));

    setMCVarDeclarationStatementsVisitor(new MCVarDeclarationStatementsPrettyPrinter(printer));
  }

  public String prettyprint(ASTTriggeredStatechartsNode node) {
    this.getPrinter().clearBuffer();
    node.accept(this.getRealThis());
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCBasisNode node) {
    this.getPrinter().clearBuffer();
    node.accept(this.getRealThis());
    return this.getPrinter().getContent();
  }

  public TriggeredStatechartsDelegatorVisitor getRealThis() {
    return this.realThis;
  }

  public IndentPrinter getPrinter() {
    return printer;
  }

}
