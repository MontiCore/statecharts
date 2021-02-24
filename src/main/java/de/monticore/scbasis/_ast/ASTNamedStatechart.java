/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

import java.util.Optional;

public class ASTNamedStatechart extends ASTNamedStatechartTOP{
  
  public Optional<String> getSCName(){
    return Optional.of(getName());
  }
  
}
