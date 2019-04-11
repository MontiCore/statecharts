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

package de.monticore.umlsc.statechart._ast;

import java.util.Optional;

import de.monticore.symboltable.Scope;
import de.monticore.symboltable.Symbol;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;

public class ASTSCTransition extends ASTSCTransitionTOP {

	public ASTSCTransition() {
		super();
	}

	public ASTSCTransition(
			Optional<de.monticore.umlsc.statechart._ast.ASTSCStereotype> stereotype,
			String sourceName,
			String targetName,
			Optional<de.monticore.umlsc.statechart._ast.ASTSCTransitionBody> sCTransitionBody
	) {
		super(stereotype, sourceName, targetName, sCTransitionBody);
	}

	public ASTSCState getTarget() {
		if (isPresentEnclosingScope()) {
			Scope scope = getEnclosingScope();
			String name = getTargetName();
			Optional<Symbol> symbol = scope.resolve(name, SCStateSymbol.KIND);
			if (symbol.isPresent()) {
				return (ASTSCState) symbol.get().getAstNode().get();
			}
		}
		return null;
	}
	public ASTSCState getSource() {
		if (isPresentEnclosingScope()) {
			Scope scope = getEnclosingScope();
			String name = getSourceName();
			Optional<Symbol> symbol = scope.resolve(name, SCStateSymbol.KIND);
			if (symbol.isPresent()) {
				return (ASTSCState) symbol.get().getAstNode().get();
			}
		}
		return null;
	}

}
