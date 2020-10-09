/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._symboltable;

import java.util.Deque;

/**
 * This class fixes handling of optional Statechart name
 */
public class SCBasisSymbolTableCreator  extends SCBasisSymbolTableCreatorTOP  {
  
  public SCBasisSymbolTableCreator(ISCBasisScope enclosingScope) {
    super(enclosingScope);
  }
  
  public SCBasisSymbolTableCreator(
      Deque<? extends ISCBasisScope> scopeStack) {
    super(scopeStack);
  }
  
 public  void visit (de.monticore.scbasis._ast.ASTStatechart node)  {
   if(node.isPresentName()) {
     de.monticore.scbasis._symboltable.StatechartSymbol symbol = create_Statechart(node);
     addToScopeAndLinkWithNode(symbol, node);
   }
 }
  
  public  void endVisit (de.monticore.scbasis._ast.ASTStatechart node)  {
    if(node.isPresentName()) {
      initialize_Statechart(node.getSymbol(), node);
    }
  }
}
