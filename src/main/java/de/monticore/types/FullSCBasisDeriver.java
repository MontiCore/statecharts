/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.monticore.types.check.*;

public class FullSCBasisDeriver extends AbstractDerive {

  public FullSCBasisDeriver() {
    this(SCBasisMill.traverser());
  }

  public FullSCBasisDeriver(SCBasisTraverser traverser) {
    super(traverser);
    init(traverser);
  }

  protected void init(SCBasisTraverser traverser) {
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
  }

}
