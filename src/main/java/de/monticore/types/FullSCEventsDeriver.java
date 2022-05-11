/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.scevents.SCEventsMill;
import de.monticore.scevents._visitor.SCEventsTraverser;
import de.monticore.types.check.*;

public class FullSCEventsDeriver extends AbstractDerive {

  public FullSCEventsDeriver(){
    this(SCEventsMill.traverser());
  }

  public FullSCEventsDeriver(SCEventsTraverser traverser) {
    super(traverser);
    init(traverser);
  }

  public void init(SCEventsTraverser traverser) {
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
