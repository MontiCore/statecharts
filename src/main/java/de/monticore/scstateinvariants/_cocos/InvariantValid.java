/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstateinvariants._cocos;

import de.monticore.scstateinvariants._ast.ASTSCInvState;
import de.monticore.types.check.*;
import de.monticore.types3.SymTypeRelations;
import de.monticore.types3.TypeCheck3;
import de.se_rwth.commons.logging.Log;

public class InvariantValid implements SCStateInvariantsASTSCInvStateCoCo {

  public static final String ERROR_CODE = "0xCC101";

  @Override
  public void check(ASTSCInvState node) {
    SymTypeExpression symType = TypeCheck3.typeOf(node.getExpression());

    if (!symType.isObscureType() && !SymTypeRelations.isBoolean(symType)) {
      Log.error(ERROR_CODE + " Invariant of " + node.getName() + " not of type boolean", node.get_SourcePositionStart());
    }
  }
}
