/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.types.check.*;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

import java.util.Optional;

public class DeriveSymTypeOfUMLStatecharts implements IDerive {
  
  protected TypeCheckResult typeCheckResult;
  protected UMLStatechartsTraverser traverser;
  
  public DeriveSymTypeOfUMLStatecharts() {
    init();
  }
  
  
  @Override public void init() {
  
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = UMLStatechartsMill.traverser();
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
  
  @Override public UMLStatechartsTraverser getTraverser() {
    return traverser;
  }
}
