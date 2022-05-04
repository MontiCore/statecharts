/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sctransitions4code._cocos;

import com.google.common.base.Preconditions;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.types.check.AbstractDerive;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.TypeCheck;
import de.monticore.types.check.TypeCheckResult;
import de.se_rwth.commons.logging.Log;

import java.util.Optional;

/**
 * Checks that the preconditions of transitions evaluate to boolean values.
 */
public class TransitionPreconditionsAreBoolean implements SCTransitions4CodeASTTransitionBodyCoCo {

  public static final String ERROR_CODE = "0xCC111";

  protected static final String MESSAGE =
    " Guard expressions must be boolean. Your guard expression is of type '%s'.";

  /**
   * Used to extract the type to which the transition precondition evaluates to.
   */
  protected final AbstractDerive typeDeriver;

  /**
   * @param typeDeriver Used to derive the type that the {@link ASTExpression} representing the transition precondition
   *                    evaluates to.
   */
  public TransitionPreconditionsAreBoolean(AbstractDerive typeDeriver) {
    this.typeDeriver = typeDeriver;
  }

  /**
   * Checks to which type the {@code expression} evaluates to and returns it, wrapped in an optional. If the expression
   * does not evaluate to a type, e.g., because it is malformed, the returned optional is empty.
   */
  protected Optional<SymTypeExpression> extractTypeOf(ASTExpression expression) {
    Preconditions.checkNotNull(expression);
    TypeCheckResult tcr = this.typeDeriver.deriveType(expression);
    if (tcr.isPresentResult()) {
      return Optional.of(tcr.getResult());
    } else {
      return Optional.empty();
    }
  }

  @Override
  public void check(ASTTransitionBody node) {
    if(node.isPresentPre()) {
      Optional<SymTypeExpression> preType = extractTypeOf(node.getPre());
      if(preType.isPresent() && !TypeCheck.isBoolean(preType.get())) {
        Log.error(
          String.format(ERROR_CODE + MESSAGE, preType.get().print()),
          node.get_SourcePositionStart(), node.get_SourcePositionEnd()
        );
      }
    }
  }
}
