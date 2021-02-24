/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstateinvariants._cocos;

import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.types.check.TypeCheck;
import de.se_rwth.commons.logging.Log;

public class InvariantValid implements SCStateInvariantsASTSCInvStateCoCo {
  protected TypeCheck deriveSymType;
  
  public InvariantValid(TypeCheck deriveSymType) {
    this.deriveSymType = deriveSymType;
  }
  
  public static final String ERROR_CODE = "0xCC101";
  
  @Override public void check(ASTSCInvState node) {
    if(!TypeCheck.isBoolean(deriveSymType.typeOf(node.getExpression()))){
      Log.error(ERROR_CODE + " Invariant of " + node.getName() + " not of type boolean", node.get_SourcePositionStart());
    }
  }
}
