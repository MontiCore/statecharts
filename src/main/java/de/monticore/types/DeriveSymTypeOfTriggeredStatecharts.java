/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;
import de.monticore.types.check.*;

public class DeriveSymTypeOfTriggeredStatecharts implements IDerive {

  protected TypeCheckResult typeCheckResult;
  protected TriggeredStatechartsTraverser traverser;

  public DeriveSymTypeOfTriggeredStatecharts() {
    init();
  }


  public void init() {

    this.typeCheckResult = new TypeCheckResult();
    this.traverser = TriggeredStatechartsMill.traverser();
    // initializes visitors used for typechecking
    final DeriveSymTypeOfLiterals deriveSymTypeOfLiterals = new DeriveSymTypeOfLiterals();
    deriveSymTypeOfLiterals.setTypeCheckResult(getTypeCheckResult());
    traverser.add4MCLiteralsBasis(deriveSymTypeOfLiterals);

    final DeriveSymTypeOfMCCommonLiterals deriveSymTypeOfMCCommonLiterals = new DeriveSymTypeOfMCCommonLiterals();
    deriveSymTypeOfMCCommonLiterals.setTypeCheckResult(getTypeCheckResult());
    traverser.add4MCCommonLiterals(deriveSymTypeOfMCCommonLiterals);

    final DeriveSymTypeOfExpression deriveSymTypeOfExpression = new DeriveSymTypeOfExpression();
    deriveSymTypeOfExpression.setTypeCheckResult(getTypeCheckResult());
    traverser.add4ExpressionsBasis(deriveSymTypeOfExpression);
    traverser.setExpressionsBasisHandler(deriveSymTypeOfExpression);

    DeriveSymTypeOfCommonExpressions deriveSymTypeOfCommonExpressions = new DeriveSymTypeOfCommonExpr();
    deriveSymTypeOfCommonExpressions.setTypeCheckResult(getTypeCheckResult());
    traverser.add4CommonExpressions(deriveSymTypeOfCommonExpressions);
    traverser.setCommonExpressionsHandler(deriveSymTypeOfCommonExpressions);

  }

  protected TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }

  protected TriggeredStatechartsTraverser getTraverser() {
    return traverser;
  }

  @Override
  public TypeCheckResult deriveType(ASTExpression expr) {
    this.getTypeCheckResult().reset();
    expr.accept(this.getTraverser());
    return this.getTypeCheckResult().copy();
  }

  @Override
  public TypeCheckResult deriveType(ASTLiteral lit) {
    this.getTypeCheckResult().reset();
    lit.accept(this.getTraverser());
    return this.getTypeCheckResult().copy();
  }
}
