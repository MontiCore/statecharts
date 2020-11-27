/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.sctransitions4modelling._ast.ASTSCTransitions4ModellingNode;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._ast.ASTUMLStatechartsNode;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class UMLStatechartsFullPrettyPrinter {
  protected IndentPrinter printer;
  
  protected UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();

  public UMLStatechartsFullPrettyPrinter() {
    this(new IndentPrinter());
  }

  public UMLStatechartsFullPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;

    // UMLStereotype, MCBasicTypes, ExpressionsBasis
//    setUMLStereotypeVisitor(new UMLStereotypePrettyPrinter(printer));
//    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
    //    setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));
    //    setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
    //    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));
    //    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
    //    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
    //    setMCReturnStatementsVisitor(new MCReturnStatementsPrettyPrinter(printer));
    //    setMCVarDeclarationStatementsVisitor(new MCVarDeclarationStatementsPrettyPrinter(printer));
  
  
    traverser.setSCBasisHandler(new SCBasisPrettyPrinter(printer));
    traverser.setUMLStatechartsHandler(new UMLStatechartsPrettyPrinter(printer));
  
    traverser.setSCActionsHandler(new SCActionsPrettyPrinter(printer));
    traverser.setSCDoActionsHandler(new SCDoActionsPrettyPrinter(printer));
    traverser.setSCStateHierarchyHandler(new SCStateHierarchyPrettyPrinter(printer));
    traverser.setSCStateInvariantsHandler(new SCStateInvariantsPrettyPrinter(printer));
    traverser.setSCCompletenessHandler(new SCCompletenessPrettyPrinter(printer));
    traverser.setSCTransitions4ModellingHandler(new SCTransitions4ModellingPrettyPrinter(printer));
    traverser.setSCTransitions4CodeHandler(new SCTransitions4CodePrettyPrinter(printer));
    traverser.setSCEventsHandler(new SCEventsPrettyPrinter(printer));

  }

  public String prettyprint(ASTUMLStatechartsNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCTransitions4ModellingNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public String prettyprint(ASTSCBasisNode node) {
    this.getPrinter().clearBuffer();
    node.accept(traverser);
    return this.getPrinter().getContent();
  }

  public IndentPrinter getPrinter() {
    return printer;
  }

}
