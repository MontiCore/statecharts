/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCState;
import de.se_rwth.commons.logging.Log;

/**
 * An ante block must be used together with an {@code initial} state modifier.
 * Note that the coco does not check whether the state is actually an initial
 * state of the statechart (a state is only an initial state of the statechart
 * if it and all its super states are initial states of their super state).
 */
public class AnteBlockOnlyWithInitialStateModifier implements SCBasisASTSCStateCoCo {

  public static final String ERROR_CODE = "0xCC112";

  protected static final String MESSAGE =
    "Initialization actions may only be specified for initial states, but '%s' is not initial.";

  @Override
  public void check(ASTSCState node) {
    if (node.isPresentSCSAnte() && !node.getSCModifier().isInitial()) {
      Log.error(ERROR_CODE + " " + String.format(MESSAGE, node.getName()),
        node.get_SourcePositionStart(), node.get_SourcePositionEnd()
      );
    }
  }
}
