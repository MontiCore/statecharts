/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scevents._symboltable;

import de.monticore.scevents._ast.ASTSCFuncEventDef;
import de.monticore.scevents._ast.ASTSCFuncEventParameter;
import de.monticore.scevents._visitor.SCEventsVisitor2;
import de.monticore.symbols.basicsymbols._symboltable.FunctionSymbol;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.monticore.types3.TypeCheck3;

public class SCEventsSTCompleter implements SCEventsVisitor2 {

  @Override public void visit(ASTSCFuncEventDef ast) {
    FunctionSymbol funcSym = ast.getSymbol();
    SymTypeExpression typeResult;
    if(ast.isPresentMCReturnType() && ast.getMCReturnType().isPresentMCType()) {
      typeResult = TypeCheck3.symTypeFromAST(ast.getMCReturnType().getMCType());
    } else{
      typeResult = SymTypeExpressionFactory.createTypeVoid();
    }
    funcSym.setType(typeResult);
  }

  @Override public void visit(ASTSCFuncEventParameter ast) {
    ast.getSymbol().setType(TypeCheck3.symTypeFromAST(ast.getMCType()));
  }
}
