/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scevents._cocos;

import de.monticore.scevents._ast.ASTSCFuncEventDef;
import de.se_rwth.commons.logging.Log;

public class NonCapitalEventNames implements SCEventsASTSCFuncEventDefCoCo {
  
  public static final String ERROR_CODE = "0xCC110";
  
  @Override public void check(ASTSCFuncEventDef node) {
    if(!Character.isLowerCase((node.getName().charAt(0)))){
      Log.warn(ERROR_CODE + " The event name " + node.getName() + " should start with a lower case letter."
          , node.get_SourcePositionStart());
    }
  }
}
