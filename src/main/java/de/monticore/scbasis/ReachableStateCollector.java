/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor2;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects all states reachable from a given state
 */
public class ReachableStateCollector implements SCBasisVisitor2 {


  protected final String fromState;
  protected final Set<String> reachableStates = new HashSet<>();

  public ReachableStateCollector(String fromState) {
    this.fromState = fromState;
  }

  @Override
  public void visit(ASTSCTransition node) {
    if (node.getSourceName().equals(this.fromState)) {
      this.reachableStates.add(node.getTargetName());
    }
  }

  public String getFromState() {
    return fromState;
  }

  public Set<String> getReachableStates() {
    return reachableStates;
  }
}
