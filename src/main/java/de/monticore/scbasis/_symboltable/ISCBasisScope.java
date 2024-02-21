/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._symboltable;

import de.monticore.symboltable.modifiers.AccessModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface ISCBasisScope extends ISCBasisScopeTOP {

  /**
   * Overridden to ensure states are found in substates without qualification
   */
  @Override
  default List<SCStateSymbol> continueAsSCStateSubScope(boolean foundSymbols, String name, AccessModifier modifier,
                                                        Predicate<SCStateSymbol> predicate) {

    List<SCStateSymbol> resultList = new ArrayList<>();
    setSCStateSymbolsAlreadyResolved(false);
    if (checkIfContinueAsSubScope(name)) {
      for (String remainingSymbolName : getRemainingNameForResolveDown(name)) {
        resultList.addAll(this.resolveSCStateDownMany(foundSymbols, remainingSymbolName, modifier, predicate));
      }
    } else {
      resultList.addAll(this.resolveSCStateDownMany(foundSymbols, name, modifier, predicate));
    }
    return resultList;
  }

}
