/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._cocos;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTStatechart;
import de.se_rwth.commons.logging.Log;

import java.util.List;
import java.util.stream.Collectors;

public class MaxOneInitialState implements SCBasisASTStatechartCoCo {

  public static final String ERROR_CODE = "CC114";
  public static final String ERROR_MSG = "Statecharts must not have more than one initial state";


  @Override
  public void check(ASTStatechart automaton) {
    // Look at top-level states only
    List<ASTSCState> initialStates = automaton.streamSCStatechartElements()
      .filter(ASTSCState.class::isInstance)
      .map(ASTSCState.class::cast)
      .filter(s -> s.getSCModifier().isInitial())
      .collect(Collectors.toList());

    int count = initialStates.size();
    if (count > 1) {
      Log.error(ERROR_CODE + " " + ERROR_MSG + ". Following states are currently initial: "
        + String.join(", ", initialStates.stream().map(ASTSCState::getName).collect(Collectors.toList())),
        initialStates.get(1).get_SourcePositionStart(), initialStates.get(1).get_SourcePositionEnd()
      );
    }
  }
}
