/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

public class ASTSCTransition extends ASTSCTransitionTOP {
  
  @Override
  protected void updateSourceNameSymbol() {
    if (getEnclosingScope() != null && (sourceNameSymbol == null || !getSourceName().equals(sourceNameSymbol.getName()))) {
      sourceNameSymbol = getEnclosingScope().resolveSCStateMany(getSourceName()).stream().findFirst().orElse(null);
    }
  }

  @Override
  protected void updateTargetNameSymbol() {
    if (getEnclosingScope() != null && (targetNameSymbol == null || !getTargetName().equals(targetNameSymbol.getName()))) {
      targetNameSymbol = getEnclosingScope().resolveSCStateMany(getTargetName()).stream().findFirst().orElse(null);
    }
  }
}
