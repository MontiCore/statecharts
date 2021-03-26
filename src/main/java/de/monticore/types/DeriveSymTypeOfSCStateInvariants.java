/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scstateinvariants.SCStateInvariantsMill;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsTraverser;
import de.monticore.scstateinvariants._visitor.SCStateInvariantsVisitor2;
import de.monticore.types.check.*;

import java.util.Optional;

public class DeriveSymTypeOfSCStateInvariants implements IDerive,
    SCStateInvariantsVisitor2 {
  
  protected TypeCheckResult typeCheckResult;
  protected SCStateInvariantsTraverser traverser;
  
  public DeriveSymTypeOfSCStateInvariants() {
    init();
  }
  
  @Override public void init() {
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = SCStateInvariantsMill.traverser();
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
  
  public TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }

  @Override
  public Optional<SymTypeExpression> getResult() {
    if(typeCheckResult.isPresentCurrentResult()){
      return Optional.ofNullable(typeCheckResult.getCurrentResult());
    }
    return Optional.empty();
  }
  
  @Override public SCStateInvariantsTraverser getTraverser() {
    return traverser;
  }
}
