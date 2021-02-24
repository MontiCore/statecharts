/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCTransition;
import de.se_rwth.commons.logging.Log;

public class TransitionSourceTargetExists implements SCBasisASTSCTransitionCoCo{
  
  public static final String SOURCE_ERROR_CODE = "0xCC103";
  public static final String TARGET_ERROR_CODE = "0xCC104";
  
  @Override
  public void check(ASTSCTransition node) {
  
    if (!node.isPresentSourceNameSymbol()) {
      Log.error(
          SOURCE_ERROR_CODE + " Source state of the transition is missing.",
          node.get_SourcePositionStart());
    }
  
    if (!node.isPresentTargetNameSymbol()) {
      Log.error(
          TARGET_ERROR_CODE + " Target state of the transition is missing.",
          node.get_SourcePositionStart());
    }
  }
  
}
