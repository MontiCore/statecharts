/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor2;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects all states
 */
public class StateCollector implements SCBasisVisitor2 {


  protected final List<ASTSCState> states = new ArrayList<>();

  @Override
  public void visit(ASTSCState node) {
    this.states.add(node);
  }

  public List<ASTSCState> getStates() {
    return states;
  }
}
