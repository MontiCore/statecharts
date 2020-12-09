/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scevents._symboltable;

import de.monticore.scevents._ast.ASTSCFuncEventDef;
import de.monticore.scevents._ast.ASTSCFuncEventParameter;
import de.monticore.scevents._visitor.SCEventsVisitor2;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import de.monticore.symbols.basicsymbols._symboltable.VariableSymbol;
import de.monticore.types.DeriveSymTypeOfUMLStatecharts;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.se_rwth.commons.logging.Log;

import java.util.Optional;

public class SCEventsSTCompleter implements SCEventsVisitor2 {
  
  DeriveSymTypeOfUMLStatecharts typeChecker = new DeriveSymTypeOfUMLStatecharts();
  
  
  @Override public void visit(ASTSCFuncEventDef ast) {
    FunctionSymbol funcSym = ast.getSymbol();
    Optional<SymTypeExpression> typeResult = Optional.empty();
    if(ast.isPresentMCReturnType() && ast.getMCReturnType().isPresentMCType()) {
      typeResult = typeChecker.calculateType(ast.getMCReturnType().getMCType());
    } else{
      typeResult = Optional.of(SymTypeExpressionFactory.createTypeVoid());
    }
    if(!typeResult.isPresent()){
      Log.error(String.format("0x5C003: The return type of the event %s could not be calculated",
          ast.getName()));
    }
    funcSym.setReturnType(typeResult.get());
  }
  
  @Override public void visit(ASTSCFuncEventParameter ast) {
    VariableSymbol varSymbol = ast.getSymbol();
      Optional<SymTypeExpression> typeResult = typeChecker.calculateType(ast.getMCType());
    if(!typeResult.isPresent()){
      Log.error(String.format("0x5C004: The type of the event paremeter %s could not be calculated",
          ast.getName()));
    }
    varSymbol.setType(typeResult.get());
  }
}
