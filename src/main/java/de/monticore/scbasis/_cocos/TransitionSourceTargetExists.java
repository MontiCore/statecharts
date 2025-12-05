/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCTransition;
import de.se_rwth.commons.logging.Log;

public class TransitionSourceTargetExists implements SCBasisASTSCTransitionCoCo {

  public static final String CANT_FIND_SOURCE_ERROR_CODE = "0xCC103";
  public static final String CANT_FIND_TARGET_ERROR_CODE = "0xCC104";

  public static final String CANT_FIND_SOURCE_ERROR_MSG = "Cannot resolve source state '%s'.";
  public static final String CANT_FIND_TARGET_ERROR_MSG = "Cannot resolve target state '%s'.";

  @Deprecated
  public static final String SOURCE_ERROR_CODE = CANT_FIND_SOURCE_ERROR_CODE;
  @Deprecated
  public static final String TARGET_ERROR_CODE = CANT_FIND_TARGET_ERROR_CODE;

  @Override
  public void check(ASTSCTransition node) {

    if (!node.isPresentSourceNameSymbol()) {
      Log.error(CANT_FIND_SOURCE_ERROR_CODE + " " + String.format(CANT_FIND_SOURCE_ERROR_MSG, node.getSourceName()),
        node.get_SourcePositionStart(),
        node.get_SourcePositionStart()
      );
    }

    if (!node.isPresentTargetNameSymbol()) {
      Log.error(CANT_FIND_TARGET_ERROR_CODE + " " + String.format(CANT_FIND_TARGET_ERROR_MSG, node.getSourceName()),
        node.get_SourcePositionStart(),
        node.get_SourcePositionEnd()
      );
    }
  }
}
