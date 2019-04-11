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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.monticore.symboltable.Symbol;

public class ASTSCState extends ASTSCStateTOP {

	public ASTSCState() {
		super();
	}

	public ASTSCState(
			Optional<ASTCompleteness> completeness,
			de.monticore.umlsc.statechart._ast.ASTSCModifier sCModifier,
			String name,
			Optional<de.monticore.umlsc.statechart._ast.ASTInvariant> invariant,
			Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> entryAction,
			Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> doAction,
			Optional<de.monticore.umlsc.statechart._ast.ASTSCAction> exitAction,
			java.util.List<de.monticore.umlsc.statechart._ast.ASTSCState> sCStates,
			java.util.List<de.monticore.umlsc.statechart._ast.ASTSCTransition> sCTransitions,
			java.util.List<de.monticore.umlsc.statechart._ast.ASTSCCode> sCCodes,
			java.util.List<de.monticore.umlsc.statechart._ast.ASTSCInternTransition> sCInternTransitions,
			Optional<String> bracket
	)  {
		super(completeness, sCModifier, name, invariant, entryAction, doAction, exitAction, sCStates, sCTransitions,
				sCCodes, sCInternTransitions, bracket );
	}

	public Set<ASTSCTransition> getOutgoingTransitions() {
		Set<ASTSCTransition> result = new HashSet<ASTSCTransition>();
		if (isPresentEnclosingScope()) {
			Symbol spanning = getEnclosingScope().getSpanningSymbol().get();
			if (spanning.getAstNode().get() instanceof ASTStatechart) {
				ASTStatechart sc = (ASTStatechart) spanning.getAstNode().get();
				for (ASTSCTransition t : sc.getSCTransitionList()) {
					if (t.getSourceName().equals(getName())) {
						result.add(t);
					}
				}
				return result;
			} else {
				// TODO in case internal / hierarchical transitions need to be
				// resolved
			}
		}
		return result;
	}

}
