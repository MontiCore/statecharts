/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects all states reachable from a given state
 */
public class ReachableStateVisitor implements SCBasisVisitor {

  protected SCBasisVisitor realThis = this;

  protected final String fromState;
  protected final Set<String> reachableStates = new HashSet<>();

  public ReachableStateVisitor(String fromState) {
    this.fromState = fromState;
  }

  @Override
  public SCBasisVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCBasisVisitor realThis) {
    this.realThis = realThis;
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
