/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor2;

import java.util.HashSet;
import java.util.Set;

/**
 * Collects all initial states
 */
public class InitialStateCollector implements SCBasisVisitor2 {

  protected final Set<String> states = new HashSet<>();

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
