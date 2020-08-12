/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ASTStatechart extends ASTStatechartTOP {

  public List<ASTSCState> getSCStateList() {
    return this.streamSCStates().collect(Collectors.toList());
  }

  public Stream<ASTSCState> streamSCStates() {
    return super.streamSCStatechartElements().filter(e -> e instanceof ASTSCState).map(e -> (ASTSCState) e);
  }

  public List<ASTSCTransition> getSCTransitionList() {
    return this.streamSCTransitions().collect(Collectors.toList());
  }

  public Stream<ASTSCTransition> streamSCTransitions() {
    return super.streamSCStatechartElements().filter(e -> e instanceof ASTSCTransition).map(e -> (ASTSCTransition) e);
  }
}
