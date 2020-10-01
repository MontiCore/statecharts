/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstatehierarchy;

import de.monticore.scbasis.ReachableStateVisitor;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyVisitor;


/**
 * Extends the ReachableStateVisitor for hierarchical states
 */
public class HierarchicalReachableStates extends ReachableStateVisitor implements SCStateHierarchyVisitor {

  protected SCStateHierarchyVisitor realThis = this;


  public HierarchicalReachableStates(String fromState) {
    super(fromState);
  }

  @Override
  public SCStateHierarchyVisitor getRealThis() {
    return realThis;
  }

  @Override
  public void setRealThis(SCStateHierarchyVisitor realThis) {
    this.realThis = realThis;
  }

}
