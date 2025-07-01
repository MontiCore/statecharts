/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sctransitions4code._cocos;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._cocos.SCBasisASTSCStateCoCo;
import de.monticore.sctransitions4code.SCTransitions4CodeMill;
import de.monticore.sctransitions4code._ast.ASTAnteAction;
import de.se_rwth.commons.logging.Log;

/**
 * An ante block should only be used if the states is marked as initial state,
 * that is, the state has the {@code initial} modifier. This coco checks this
 * coding convention. Note that the coco does not check whether the state is
 * an actually initial state of the statechart (a state is only an initial
 * state if it and all its super states are initial states).
 */
public class AnteBlocksOnlyForStatesMarkedInitial implements SCBasisASTSCStateCoCo {

  public static final String ERROR_CODE = "0xCC112";

  protected static final String MESSAGE =
    "Initialization actions may only be specified for initial states, but '%s' is not initial.";

  @Override
  public void check(ASTSCState node) {
    if (!SCBasisMill.typeDispatcher().isSCBasisASTSCEmptyAnte(node.getSCSAnte())
      && !node.getSCModifier().isInitial()) {
      Log.warn(ERROR_CODE + " " + String.format(MESSAGE, node.getName()),
        node.get_SourcePositionStart()
      );
    }
  }
}
