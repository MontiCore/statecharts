/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._symboltable;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.symboltable.modifiers.AccessModifier;

import java.util.*;
import java.util.function.Predicate;

import static de.se_rwth.commons.Joiners.DOT;

public interface ISCBasisScope extends ISCBasisScopeTOP{
  
  /**
   * Overridden to ensure states are found in substates without qualification
   */@Override
  default List<SCStateSymbol> continueAsSCStateSubScope (boolean foundSymbols,String name, AccessModifier modifier,
      Predicate<SCStateSymbol> predicate)  {
  
    List<SCStateSymbol> resultList = new ArrayList<>();
    setSCStateSymbolsAlreadyResolved(false);
    for(String remainingSymbolName: getRemainingNameForResolveDown(name)) {
      resultList.addAll(this.resolveSCStateDownMany(foundSymbols, remainingSymbolName, modifier, predicate));
    }
    return resultList;
  }

  @Override
  default public Optional<TypeSymbol> filterType(String name, com.google.common.collect.LinkedListMultimap<String, de.monticore.symbols.basicsymbols._symboltable.TypeSymbol> symbols) {

    final Set<TypeSymbol> resolvedSymbols = new LinkedHashSet<>();
    final String simpleName = de.se_rwth.commons.Names.getSimpleName(name);

    for (de.monticore.symbols.basicsymbols._symboltable.TypeSymbol symbol : symbols.values()) {
      if (symbol.getName().equals(name) || symbol.getFullName().equals(name)) {
        resolvedSymbols.add(symbol);
      }
    }

    return getResolvedOrThrowException(resolvedSymbols);
  }

  @Override
  default boolean checkIfContinueAsSubScope(String symbolName) {
    if (this.isExportingSymbols()) {
      final List<String> nameParts = getNameParts(symbolName).toList();

      if (nameParts.size() > 1) {
        final String firstNamePart = nameParts.get(0);
        // A scope that exports symbols usually has a name.
        if (this.isPresentName()) {
          return symbolName.startsWith(this.getName()) && symbolName.length() > this.getName().length();
        }
        else {
          return firstNamePart.equals("");
        }
      }
    }

    return false;
  }

  default List<String> getRemainingNameForResolveDown(String symbolName) {
    final FluentIterable<String> nameParts = getNameParts(symbolName);
    if (nameParts.size() > 1 && this.isPresentName() && symbolName.startsWith(this.getName())) {
      return Lists.newArrayList(symbolName.replaceFirst(this.getName() + "\\.", ""));
    }

    return Lists.newArrayList(symbolName);
  }

}
