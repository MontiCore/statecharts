/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scstateinvariants.SCStateInvariantsMill;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsTraverser;
import de.monticore.types.check.*;

/**
 * Use TypeCheck3 instead.
 */
@Deprecated
public class FullSCStateInvariantsDeriver extends AbstractDerive {

  public FullSCStateInvariantsDeriver() {
    this(SCStateInvariantsMill.traverser());
  }

  public FullSCStateInvariantsDeriver(SCStateInvariantsTraverser traverser) {
    super(traverser);
    init(traverser);
  }

  public void init(SCStateInvariantsTraverser traverser) {
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

  }

}
