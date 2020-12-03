/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.literals.prettyprint.MCCommonLiteralsPrettyPrinter;
import de.monticore.scbasis._ast.ASTSCBasisNode;
import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.statements.prettyprint.MCVarDeclarationStatementsPrettyPrinter;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._ast.ASTTriggeredStatechartsNode;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;

public class TriggeredStatechartsFullPrettyPrinter {
  protected IndentPrinter printer;
  
  protected TriggeredStatechartsTraverser traverser = TriggeredStatechartsMill.traverser();
  

  public TriggeredStatechartsFullPrettyPrinter() {
    this(new IndentPrinter());
  }

  public TriggeredStatechartsFullPrettyPrinter(IndentPrinter printer) {
    this.printer = printer;

    // SC pretty printer
    traverser.setSCBasisHandler(new SCBasisPrettyPrinter(printer));
    traverser.setSCActionsHandler(new SCActionsPrettyPrinter(printer));
    traverser.setSCStateHierarchyHandler(new SCStateHierarchyPrettyPrinter(printer));
    traverser.setSCTransitions4CodeHandler(new SCTransitions4CodePrettyPrinter(printer));
    
    // UML Stereotypes, MCBasicTypes, ExpressionBasis
    traverser.setUMLStereotypeHandler(new UMLStereotypePrettyPrinter(printer));
    MCCommonLiteralsPrettyPrinter mcCommonLiteralsPP = new MCCommonLiteralsPrettyPrinter(printer);
    traverser.setMCCommonLiteralsHandler(mcCommonLiteralsPP);
    traverser.add4MCCommonLiterals(mcCommonLiteralsPP);
    traverser.setMCBasicTypesHandler(new MCBasicTypesPrettyPrinter(printer));
    traverser.setExpressionsBasisHandler(new ExpressionsBasisPrettyPrinter(printer));
    
    // Common Expressions, MCCommonStatements, MCReturnStatements
    traverser.setCommonExpressionsHandler(new CommonExpressionsPrettyPrinter(printer));
    traverser.setMCCommonStatementsHandler(new MCCommonStatementsPrettyPrinter(printer));
    traverser.setMCVarDeclarationStatementsHandler(new MCVarDeclarationStatementsPrettyPrinter(printer));
  

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
