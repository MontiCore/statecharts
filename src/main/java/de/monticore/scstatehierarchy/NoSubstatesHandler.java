/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scstatehierarchy;

import de.monticore.scstatehierarchy._ast.ASTSCHierarchyBody;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyHandler;
import de.monticore.scstatehierarchy._visitor.SCStateHierarchyTraverser;

/**
 * Collects all states
 */
public class NoSubstatesHandler implements SCStateHierarchyHandler {
  
  protected SCStateHierarchyTraverser traverser;
  
  @Override public void handle(ASTSCHierarchyBody node) {
    // empty on purpose, do no visit substates
  }
  
  @Override public SCStateHierarchyTraverser getTraverser() {
    return traverser;
  }
  
  @Override public void setTraverser(SCStateHierarchyTraverser traverser) {
    this.traverser = traverser;
  }
}
