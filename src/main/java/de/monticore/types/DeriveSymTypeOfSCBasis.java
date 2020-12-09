/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.expressions.expressionsbasis._visitor.ExpressionsBasisTraverser;
import de.monticore.literals.mccommonliterals._ast.ASTSignedLiteral;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.types.check.*;
import de.monticore.types.mcbasictypes._ast.ASTMCType;

import java.util.Optional;

public class DeriveSymTypeOfSCBasis implements ITypesCalculator, SCBasisVisitor2 {
  
  protected TypeCheckResult typeCheckResult;
  protected SCBasisTraverser traverser;
  
  public DeriveSymTypeOfSCBasis() {
    this.typeCheckResult = new TypeCheckResult();
    this.traverser = SCBasisMill.traverser();
    init();
  }
  
  @Override public void init() {
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
    
      final SynthesizeSymTypeFromMCBasicTypes synthesizeSymTypeFromMCBasicTypes = new SynthesizeSymTypeFromMCBasicTypes();
      synthesizeSymTypeFromMCBasicTypes.setTypeCheckResult(getTypeCheckResult());
    traverser.add4MCBasicTypes(synthesizeSymTypeFromMCBasicTypes);
    
  }
  
  public TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }
  
  public Optional<SymTypeExpression> calculateType(ASTMCType type) {
    type.accept(traverser);
    if (getTypeCheckResult().isPresentCurrentResult()) {
      return Optional.of(getTypeCheckResult().getCurrentResult());
    }
    else {
      return Optional.empty();
    }
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
