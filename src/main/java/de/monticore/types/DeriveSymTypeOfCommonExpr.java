/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.expressions.commonexpressions._ast.ASTFieldAccessExpression;
import de.monticore.expressions.prettyprint.CommonExpressionsFullPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.symbols.basicsymbols._symboltable.VariableSymbol;
import de.monticore.types.check.DeriveSymTypeOfCommonExpressions;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SymTypeExpressionFactory;
import de.se_rwth.commons.logging.Log;

import java.util.List;
import java.util.Optional;

public class DeriveSymTypeOfCommonExpr extends DeriveSymTypeOfCommonExpressions {
  
  /**
   *  Overridden to exclude filtering for static fields as all
   *  variable symbol in type symbols are considered to be addressed via the type, e.g. Person.age,
   *  as only basic symbol are used
   */
  @Override public void traverse(ASTFieldAccessExpression expr) {
    CommonExpressionsFullPrettyPrinter printer = new CommonExpressionsFullPrettyPrinter(new IndentPrinter());
    SymTypeExpression innerResult;
    expr.getExpression().accept(getTraverser());
    if (typeCheckResult.isPresentCurrentResult()) {
      //store the type of the inner expression in a variable
      innerResult = typeCheckResult.getCurrentResult();
      //look for this type in our scope
      TypeSymbol innerResultType = innerResult.getTypeInfo();
      //search for a method, field or type in the scope of the type of the inner expression
      List<VariableSymbol> fieldSymbols = innerResult.getFieldList(expr.getName(), typeCheckResult.isType());
      Optional<TypeSymbol> typeSymbolOpt = innerResultType.getSpannedScope().resolveType(expr.getName());
      if (!fieldSymbols.isEmpty()) {
        if (fieldSymbols.size() != 1) {
          typeCheckResult.reset();
          logError("0xA1237", expr.get_SourcePositionStart());
        }
        if(!fieldSymbols.isEmpty()) {
          VariableSymbol var = fieldSymbols.get(0);
          SymTypeExpression type = var.getType();
          typeCheckResult.setField();
          typeCheckResult.setCurrentResult(type);
        }
      } else if (typeSymbolOpt.isPresent()) {
        //no variable found, test type
        TypeSymbol typeSymbol = typeSymbolOpt.get();
        boolean match = true;
        //if the last result is a type and the type is not static then it is not accessible
          SymTypeExpression wholeResult = SymTypeExpressionFactory
              .createTypeExpression(typeSymbol.getName(), typeSymbol.getEnclosingScope());
          typeCheckResult.setType();
          typeCheckResult.setCurrentResult(wholeResult);
      }else{
        typeCheckResult.reset();
        logError("0xA1316", expr.get_SourcePositionStart());
      }
    } else {
      //inner type has no result --> try to resolve a type
      String toResolve = printer.prettyprint(expr);
      Optional<TypeSymbol> typeSymbolOpt = getScope(expr.getEnclosingScope()).resolveType(toResolve);
      if (typeSymbolOpt.isPresent()) {
        TypeSymbol typeSymbol = typeSymbolOpt.get();
        SymTypeExpression type = SymTypeExpressionFactory.createTypeExpression(typeSymbol);
        typeCheckResult.setType();
        typeCheckResult.setCurrentResult(type);
      } else {
        //the inner type has no result and there is no type found
        typeCheckResult.reset();
        Log.info("package suspected", "DeriveSymTypeOfCommonExpressions");
      }
    }
  }
}
