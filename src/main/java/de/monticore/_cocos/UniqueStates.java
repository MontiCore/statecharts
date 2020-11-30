/* (c) https://github.com/MontiCore/monticore */
package de.monticore._cocos;

import com.google.common.collect.Sets;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTStatechart;
import de.monticore.scbasis._cocos.SCBasisASTStatechartCoCo;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class UniqueStates implements SCBasisASTStatechartCoCo {
  
  
  public static final String ERROR_CODE = "0x5C100";
  
  public static final String ERROR_MSG_FORMAT = " State names must be unique but %s was duplicated." ;
  
  @Override
  public void check(ASTStatechart node) {
    UMLStatechartsTraverser traverser = UMLStatechartsMill
        .traverser();
    StateCollector vis = new StateCollector();
    traverser.addSCBasisVisitor(vis);
    node.accept(traverser);
    Set<String> uniques = Sets.newHashSet();
    List<ASTSCState> duplicates = vis.getStates().stream()
        .filter(e -> !uniques.add(e.getName()))
        .collect(Collectors.toList());
    if(!duplicates.isEmpty()){
      Log.error(String.format(ERROR_CODE + ERROR_MSG_FORMAT, duplicates.get(0).getName()),
          duplicates.get(0).get_SourcePositionStart());
    }
  }
}
