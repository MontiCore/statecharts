/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

public class ASTSCStateAccess extends ASTSCStateAccessTOP {

  @Override
  protected void updateNameSymbol() {
    if (getEnclosingScope() != null && ( nameSymbol == null || !getName().equals(nameSymbol.getName()))) {
      nameSymbol = getEnclosingScope().resolveSCStateDownMany(getName()).stream().findFirst().orElse(null);
    }
  }
}
