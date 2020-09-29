/* (c) https://github.com/MontiCore/monticore */
package de.monticore._visitor;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects all initial states
 */
public class InitialStateCollectorVisitor implements SCBasisVisitor {

  private SCBasisVisitor realThis = this;

  private final Set<String> states = new HashSet<>();

  @Override
  public SCBasisVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCBasisVisitor realThis) {
    this.realThis = realThis;
  }

  @Override
  public void visit(ASTSCState node) {
    if (node.getSCModifier().isInitial()) {
      this.states.add(node.getName());
    }
  }

  public Set<String> getStates() {
    return states;
  }
}
