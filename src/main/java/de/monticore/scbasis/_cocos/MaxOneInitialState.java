/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.scbasis.StateCollector;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTStatechart;
import de.monticore.scbasis._visitor.SCBasisTraverser;
import de.se_rwth.commons.logging.Log;

import java.util.List;
import java.util.stream.Collectors;

public class MaxOneInitialState implements SCBasisASTStatechartCoCo {

  protected SCBasisTraverser t;

  public MaxOneInitialState() {
    super();
    this.t = SCBasisMill.traverser();
  }

  public MaxOneInitialState(SCBasisTraverser traverser) {
    super();
    this.t = traverser;
  }

  public static final String ERROR_CODE = "0xCC114";
  public static final String ERROR_MSG = "Statecharts must not have more than one initial state";


  @Override
  public void check(ASTStatechart node) {
    StateCollector collector = new StateCollector();
    t.add4SCBasis(collector);
    node.accept(t);

    // Look at top-level initial states only
    List<ASTSCState> initialStates = collector.getStates(0).stream().filter(x -> x.getSCModifier().isInitial()).collect(Collectors.toList());

    int count = initialStates.size();
    if (count > 1) {
      Log.error(ERROR_CODE + " " + ERROR_MSG + ". Following states are currently initial: "
        + String.join(", ", initialStates.stream().map(ASTSCState::getName).collect(Collectors.toList())),
        initialStates.get(1).get_SourcePositionStart(), initialStates.get(1).get_SourcePositionEnd()
      );
    }
  }
}
