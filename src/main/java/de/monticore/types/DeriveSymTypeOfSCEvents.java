/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.literals.mcliteralsbasis._ast.ASTLiteral;
import de.monticore.scevents.SCEventsMill;
import de.monticore.scevents._visitor.SCEventsTraverser;
import de.monticore.scevents._visitor.SCEventsVisitor2;
import de.monticore.types.check.*;

public class DeriveSymTypeOfSCEvents implements IDerive, SCEventsVisitor2 {

  protected TypeCheckResult typeCheckResult;
  protected SCEventsTraverser traverser;

  public DeriveSymTypeOfSCEvents() {
    init();
  }

  public void init() {
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

  protected TypeCheckResult getTypeCheckResult() {
    return typeCheckResult;
  }

  protected SCEventsTraverser getTraverser() {
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
