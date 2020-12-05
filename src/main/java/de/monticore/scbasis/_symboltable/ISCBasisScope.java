/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._symboltable;

import de.monticore.symboltable.modifiers.AccessModifier;

import java.util.List;
import java.util.function.Predicate;

public interface ISCBasisScope extends ISCBasisScopeTOP{
  
  default List<SCStateSymbol> continueAsSCStateSubScope (boolean foundSymbols,String name, AccessModifier modifier,
      Predicate<SCStateSymbol> predicate)  {
    
    setSCStateSymbolsAlreadyResolved(false);
    // XXX TODO, hat nicht geklappt (BR): final String remainingSymbolName = getRemainingNameForResolveDown(name);
    final String remainingSymbolName = getRemainingNameForResolveDown(name).first();
    return this.resolveSCStateDownMany(foundSymbols, remainingSymbolName, modifier, predicate);
  }
  
}
