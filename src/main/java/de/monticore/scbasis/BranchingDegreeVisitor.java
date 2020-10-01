/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Sums up the amount of outgoing transitions for every
 * state (branching degree)
 */
public class BranchingDegreeVisitor implements SCBasisVisitor {

  protected SCBasisVisitor realThis = this;

  protected final Map<String, Integer> branchingDegree = new HashMap<>();

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
    int cur = this.branchingDegree.getOrDefault(node.getSourceName(), 0);
    this.branchingDegree.put(node.getSourceName(), cur + 1);
  }

  public Map<String, Integer> getBranchingDegrees() {
    return branchingDegree;
  }
}
