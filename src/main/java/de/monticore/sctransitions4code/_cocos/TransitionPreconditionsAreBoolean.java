/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sctransitions4code._cocos;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.types.check.IDerive;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types3.SymTypeRelations;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;

import static de.monticore.symbols.basicsymbols.BasicSymbolsMill.BOOLEAN;

/**
 * Checks that the preconditions of transitions evaluate to boolean values.
 */
public class TransitionPreconditionsAreBoolean implements SCTransitions4CodeASTTransitionBodyCoCo {

  public static final String GUARD_NOT_BOOLEAN_ERROR_CODE = "0xCC111";

  public static final String GUARD_NOT_BOOLEAN_ERROR_MSG = "Expected '%s' but provided '%s'.";

  @Deprecated
  public static final String ERROR_CODE = GUARD_NOT_BOOLEAN_ERROR_CODE;

  @Deprecated
  protected static final String MESSAGE = GUARD_NOT_BOOLEAN_ERROR_MSG;

  /**
   * Used to extract the type to which the transition precondition evaluates to.
   */
  protected final IDerive typeDeriver;

  /**
   * @param typeDeriver Used to derive the type that the {@link ASTExpression} representing the transition precondition
   *                    evaluates to.
   */
  @Deprecated()
  public TransitionPreconditionsAreBoolean(IDerive typeDeriver) {
    this.typeDeriver = typeDeriver;
  }

  public TransitionPreconditionsAreBoolean() { this(null); }

  @Override
  public void check(ASTTransitionBody node) {
    if (node.isPresentPre()) {
      SymTypeExpression preType = TypeCheck3.typeOf(node.getPre());
      if (preType.isObscureType()) {
        Log.debug(() -> String.format(
          "Coco '%s' skipped for transition guard at %s. The type is obscure, an error should already have been logged.",
          this.getClass().getSimpleName(),
          node.get_SourcePositionStart()),
          "Cocos"
        );
      } else if (!SymTypeRelations.isBoolean(preType)) {
        Log.error(
          String.format(ERROR_CODE + " " + GUARD_NOT_BOOLEAN_ERROR_MSG, BOOLEAN, preType.print()),
          node.get_SourcePositionStart(), node.get_SourcePositionEnd()
        );
      }
    }
  }
}
