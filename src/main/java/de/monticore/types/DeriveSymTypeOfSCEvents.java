/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisTraverser;
import de.monticore.literals.mccommonliterals._ast.ASTSignedLiteral;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.scevents.SCEventsMill;
import de.monticore.scevents._visitor.SCEventsTraverser;
import de.monticore.scevents._visitor.SCEventsVisitor2;
import de.monticore.types.check.*;

import java.util.Optional;

public class DeriveSymTypeOfSCEvents implements ITypesCalculator, SCEventsVisitor2 {
  
  protected TypeCheckResult typeCheckResult;
  protected SCEventsTraverser traverser;
  
  public DeriveSymTypeOfSCEvents() {
    init();
  }
  
  @Override public void init() {
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = SCEventsMill.traverser();
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
  
  public TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }
  
  @Override
  public Optional<SymTypeExpression> calculateType(ASTExpression ex) {
    ex.accept(traverser);
    if (getTypeCheckResult().isPresentCurrentResult()) {
      return Optional.of(getTypeCheckResult().getCurrentResult());
    }
    else {
      return Optional.empty();
    }
  }
  
  @Override
  public Optional<SymTypeExpression> calculateType(ASTLiteral lit) {
    lit.accept(traverser);
    if (getTypeCheckResult().isPresentCurrentResult()) {
      return Optional.of(getTypeCheckResult().getCurrentResult());
    }
    else {
      return Optional.empty();
    }
  }
  
  @Override
  public Optional<SymTypeExpression> calculateType(ASTSignedLiteral lit) {
    lit.accept(traverser);
    if (getTypeCheckResult().isPresentCurrentResult()) {
      return Optional.of(getTypeCheckResult().getCurrentResult());
    }
    else {
      return Optional.empty();
    }
  }
  
  @Override public ExpressionsBasisTraverser getTraverser() {
    return traverser;
  }
}
