/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.types.check.*;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

/**
 * Use TypeCheck3 instead.
 */
@Deprecated
public class FullUMLStatechartsDeriver extends AbstractDerive {

  public FullUMLStatechartsDeriver() {
    this(UMLStatechartsMill.traverser());
  }

  public FullUMLStatechartsDeriver(UMLStatechartsTraverser traverser) {
    super(traverser);
    init(traverser);
  }

  protected void init(UMLStatechartsTraverser traverser) {
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

    DeriveSymTypeOfCommonExpressions deriveSymTypeOfCommonExpressions = new DeriveSymTypeOfCommonExpressions();
    deriveSymTypeOfCommonExpressions.setTypeCheckResult(getTypeCheckResult());
    traverser.add4CommonExpressions(deriveSymTypeOfCommonExpressions);
    traverser.setCommonExpressionsHandler(deriveSymTypeOfCommonExpressions);
  }

}
