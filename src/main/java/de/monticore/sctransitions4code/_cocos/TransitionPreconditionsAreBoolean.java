/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sctransitions4code._cocos;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.types.check.IDerive;
import de.monticore.types.check.TypeCheck;
import de.monticore.types.check.TypeCheckResult;
import de.se_rwth.commons.logging.Log;

/**
 * Checks that the preconditions of transitions evaluate to boolean values.
 */
public class TransitionPreconditionsAreBoolean implements SCTransitions4CodeASTTransitionBodyCoCo {

  public static final String ERROR_CODE = "0xCC111";

  protected static final String MESSAGE = "Guard expressions must be boolean. Your guard expression is of type '%s'.";

  public static final String ERROR_CODE_TYPE_REF_CONDITION = "0xCC113";

  protected static final String MESSAGE_TYPE_REF_CONDITION = "Your guard expression represents the type '%s'. " +
    "However, guard expressions must evaluate to boolean values (which type references do not do).";

  /**
   * Used to extract the type to which the transition precondition evaluates to.
   */
  protected final IDerive typeDeriver;

  /**
   * @param typeDeriver Used to derive the type that the {@link ASTExpression} representing the transition precondition
   *                    evaluates to.
   */
  public TransitionPreconditionsAreBoolean(IDerive typeDeriver) {
    this.typeDeriver = typeDeriver;
  }

  @Override
  public void check(ASTTransitionBody node) {
    if(node.isPresentPre()) {
      TypeCheckResult preType = this.typeDeriver.deriveType(node.getPre());

      if (!preType.isPresentResult() || preType.getResult().isObscureType()) {
        Log.debug(String.format("Coco '%s' is not checked on transition guard expression at %s, because the " +
          "expression is malformed.", this.getClass().getSimpleName(), node.get_SourcePositionStart()),
          "Cocos");

      } else if (preType.isType()) {
        Log.error(
          String.format(ERROR_CODE_TYPE_REF_CONDITION + " " + MESSAGE_TYPE_REF_CONDITION,
            preType.getResult().print()),
          node.get_SourcePositionStart(), node.get_SourcePositionEnd());

      } else if(!TypeCheck.isBoolean(preType.getResult())) {
        Log.error(
          String.format(ERROR_CODE + " " + MESSAGE, preType.getResult().print()),
          node.get_SourcePositionStart(), node.get_SourcePositionEnd()
        );
      }
    }
  }
}
