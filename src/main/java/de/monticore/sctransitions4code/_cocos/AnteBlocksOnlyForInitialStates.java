/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sctransitions4code._cocos;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._cocos.SCBasisASTSCStateCoCo;
import de.monticore.sctransitions4code._ast.ASTAnteAction;
import de.se_rwth.commons.logging.Log;

/**
 * This is an optional coco that you might want to include in your language when you only want to allow
 * {@link ASTAnteAction}s as part of initial state declarations, used to declare initialization actions. Then this coco
 * will check exactly this: That an ante statement block is only allowed for initial states.
 */
public class AnteBlocksOnlyForInitialStates implements SCBasisASTSCStateCoCo {

  public static final String ERROR_CODE = "0xCC112";

  protected static final String MESSAGE =
    "Initialization actions may only be specified for initial states, but '%s' is not initial.";

  @Override
  public void check(ASTSCState node) {
    if(node.getSCSAnte() instanceof ASTAnteAction && !node.getSCModifier().isInitial()) {
      Log.error(ERROR_CODE + " " + String.format(MESSAGE, node.getName()));
    }
  }
}
