/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.types.check.*;

public class DeriveSymTypeOfSCBasis implements IDerive, SCBasisVisitor2 {

  protected TypeCheckResult typeCheckResult;
  protected SCBasisTraverser traverser;

  public DeriveSymTypeOfSCBasis() {
    init();
  }

  protected void init() {
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

  protected TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }

  protected SCBasisTraverser getTraverser() {
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
