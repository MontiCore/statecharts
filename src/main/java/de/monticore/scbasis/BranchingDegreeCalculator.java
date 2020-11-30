/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis;

import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor2;

import java.util.HashMap;
import java.util.Map;

/**
 * Sums up the amount of outgoing transitions for every
 * state (branching degree)
 */
public class BranchingDegreeCalculator implements SCBasisVisitor2 {

  protected final Map<String, Integer> branchingDegree = new HashMap<>();

  @Override
  public void visit(ASTSCState node) {
    // Ensure states without an outgoing transition are collected
    this.branchingDegree.putIfAbsent(node.getName(), 0);
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
