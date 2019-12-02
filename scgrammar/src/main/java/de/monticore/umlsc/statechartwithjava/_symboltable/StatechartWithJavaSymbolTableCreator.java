/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechartwithjava._symboltable;

//import de.monticore.symboltable.IScope;
//import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._symboltable.IStatechartScope;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

import java.util.Deque;

/**
 * Created by eikermann on 28.06.2017.
 */
public class StatechartWithJavaSymbolTableCreator extends StatechartSymbolTableCreator implements StatechartWithJavaVisitor {
  private StatechartWithJavaSymbolTableCreator realThis = this;

  public StatechartWithJavaSymbolTableCreator(IStatechartScope enclosingScope) {
    super(enclosingScope);
  }


  public StatechartWithJavaSymbolTableCreator(final Deque<IStatechartScope> scopeStack) {
    super(scopeStack);
  }

  @Override
  public StatechartWithJavaVisitor getRealThis() {
    return this.realThis;
  }
}
