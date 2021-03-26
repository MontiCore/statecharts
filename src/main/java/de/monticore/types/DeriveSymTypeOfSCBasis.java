/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.types.check.*;

import java.util.Optional;

public class DeriveSymTypeOfSCBasis implements IDerive, SCBasisVisitor2 {
  
  protected TypeCheckResult typeCheckResult;
  protected SCBasisTraverser traverser;

  
  public DeriveSymTypeOfSCBasis() {
    init();
  }

  @Override public void init() {
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = SCBasisMill.traverser();
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

  @Override
  public Optional<SymTypeExpression> getResult() {
    if(typeCheckResult.isPresentCurrentResult()){
      return Optional.ofNullable(typeCheckResult.getCurrentResult());
    }
    return Optional.empty();
  }

  public TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }
  
  @Override public SCBasisTraverser getTraverser() {
    return traverser;
  }
}
