/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor;

import java.util.ArrayList;
import java.util.List;

/**
 * Collects all states
 */
public class StateCollector implements SCBasisVisitor {

  protected SCBasisVisitor realThis= this;

  protected final List<ASTSCState> states = new ArrayList<>();

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
    this.states.add(node);
  }

  public List<ASTSCState> getStates() {
    return states;
  }
}
