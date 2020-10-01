/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstatehierarchy;

import de.monticore.scbasis.BranchingDegreeVisitor;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyVisitor;

/**
 * Extends the Branching Degree for hierarchical states
 */
public class HierarchicalBranchingDegree extends BranchingDegreeVisitor implements SCStateHierarchyVisitor {

  protected SCStateHierarchyVisitor realThis = this;

  @Override
  public SCStateHierarchyVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCStateHierarchyVisitor realThis) {
    this.realThis = realThis;
  }

}
