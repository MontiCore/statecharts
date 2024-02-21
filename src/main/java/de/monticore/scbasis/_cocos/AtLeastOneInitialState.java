/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTStatechart;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.se_rwth.commons.logging.Log;

public class AtLeastOneInitialState implements SCBasisASTStatechartCoCo {
  
  protected SCBasisTraverser t;
  
  public AtLeastOneInitialState (){
    super();
     this.t = SCBasisMill.traverser();
  }
  
  public AtLeastOneInitialState (SCBasisTraverser traverser){
    super();
    this.t = traverser;
  }
  
  public static final String ERROR_CODE = "0xCC102";
  
  @Override
  public void check(ASTStatechart node) {
    StateCollector collector = new StateCollector();
    t.add4SCBasis(collector);
    node.accept(t);
    if(collector.getStates(0).stream().noneMatch(x -> x.getSCModifier().isInitial())){
      Log.error(ERROR_CODE + " Statecharts need at least one initial state.", 
          node.get_SourcePositionStart());
    }
  }
}
