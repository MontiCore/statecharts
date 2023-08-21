/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import com.google.common.collect.Sets;
import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTStatechart;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.se_rwth.commons.logging.Log;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UniqueStates implements SCBasisASTStatechartCoCo {

  protected SCBasisTraverser t;

  public UniqueStates (){
    super();
    this.t = SCBasisMill.traverser();
  }

  public UniqueStates (SCBasisTraverser traverser){
    super();
    this.t = traverser;
  }
  
  
  public static final String ERROR_CODE = "0xCC100";
  
  public static final String ERROR_MSG_FORMAT = " State names must be unique but %s was duplicated." ;
  
  @Override
  public void check(ASTStatechart node) {
    StateCollector collector = new StateCollector();
    t.add4SCBasis(collector);
    node.accept(t);
    Set<String> uniques = Sets.newHashSet();
    List<ASTSCState> duplicates = collector.getStates().stream()
        .filter(e -> !uniques.add(e.getName()))
        .collect(Collectors.toList());
    if(!duplicates.isEmpty()){
      Log.error(String.format(ERROR_CODE + ERROR_MSG_FORMAT, duplicates.get(0).getName()),
          duplicates.get(0).get_SourcePositionStart());
    }
  }
}
