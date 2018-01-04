/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.monticore.umlsc.statechartwithjava._symboltable;

import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

import java.util.Deque;

/**
 * Created by eikermann on 28.06.2017.
 */
public class StatechartWithJavaSymbolTableCreator extends StatechartSymbolTableCreator implements StatechartWithJavaVisitor {
  private StatechartWithJavaSymbolTableCreator realThis = this;

  public StatechartWithJavaSymbolTableCreator(ResolvingConfiguration resolvingConfig, MutableScope enclosingScope) {
    super(resolvingConfig, enclosingScope);
  }


  public StatechartWithJavaSymbolTableCreator(final ResolvingConfiguration resolvingConfig, final Deque<MutableScope> scopeStack) {
    super(resolvingConfig, scopeStack);
  }

  @Override
  public StatechartWithJavaVisitor getRealThis() {
    return this.realThis;
  }
}
