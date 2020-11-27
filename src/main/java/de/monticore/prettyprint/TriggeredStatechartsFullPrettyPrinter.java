/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._ast.ASTTriggeredStatechartsNode;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;

public class TriggeredStatechartsFullPrettyPrinter {
  protected IndentPrinter printer;
  
  protected TriggeredStatechartsTraverser traverser = TriggeredStatechartsMill.traverser();
  

  public TriggeredStatechartsFullPrettyPrinter() {
    this(new IndentPrinter());
  }

  public TriggeredStatechartsFullPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;

    // UMLStereotype, MCBasicTypes, ExpressionsBasis
//    setUMLStereotypeHandler(new UMLStereotypePrettyPrinter(printer));
//    setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));
//    setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
//    setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));
//    setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
//    setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
//    setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
//    setMCVarDeclarationStatementsVisitor(new MCVarDeclarationStatementsPrettyPrinter(printer));
  
  
    traverser.setSCBasisHandler(new SCBasisPrettyPrinter(printer));
    traverser.setSCActionsHandler(new SCActionsPrettyPrinter(printer));
    traverser.setSCStateHierarchyHandler(new SCStateHierarchyPrettyPrinter(printer));
    traverser.setSCTransitions4CodeHandler(new SCTransitions4CodePrettyPrinter(printer));
    
  

  }

  public String prettyprint(ASTTriggeredStatechartsNode node) {
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
