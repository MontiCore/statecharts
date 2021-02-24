/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scevents._cocos;

import de.monticore.scevents._ast.ASTSCFuncEventParameter;
import de.se_rwth.commons.logging.Log;

public class NonCapitalParamNames implements SCEventsASTSCFuncEventParameterCoCo {
  
  public static final String ERROR_CODE = "0xCC109";
  
  @Override public void check(ASTSCFuncEventParameter node) {
    if(!Character.isLowerCase((node.getName().charAt(0)))){
      Log.warn(ERROR_CODE + " The parameter name " + node.getName() + " should start with a lower case letter."
          , node.get_SourcePositionStart());
    }
  }
}
