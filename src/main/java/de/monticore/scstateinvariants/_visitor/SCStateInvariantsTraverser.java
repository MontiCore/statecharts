/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstateinvariants._visitor;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scstateinvariants._ast.ASTSCInvState;

public interface SCStateInvariantsTraverser extends SCStateInvariantsTraverserTOP {
  
  @Override default void handle(ASTSCInvState node) {
    if (getSCStateInvariantsHandler().isPresent()) {
      getSCStateInvariantsHandler().get().handle(node);
    } else {
      visit(node);
      traverse(node);
      endVisit(node);
      visit((ASTSCState) node);
      visit(node);
      traverse(node);
      endVisit(node);
      endVisit((ASTSCState) node);
    }
  }
}
