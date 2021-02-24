/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

import java.util.Optional;

public interface ASTStatechart extends ASTStatechartTOP {
  default Optional<String> getSCName(){
    return Optional.empty();
  }
  
  default boolean isPresentSCName(){
    return getSCName().isPresent();
  }
}
