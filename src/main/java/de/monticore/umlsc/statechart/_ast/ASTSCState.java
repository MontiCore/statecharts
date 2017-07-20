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
import java.util.Set;

import de.monticore.symboltable.Symbol;

public class ASTSCState extends ASTSCStateTOP {

	public ASTSCState() {
		super();
	}

	public ASTSCState(ASTCompleteness completeness, ASTSCModifier sCModifier, String name, ASTInvariant invariant,
			ASTSCAction entryAction, ASTSCAction doAction, ASTSCAction exitAction, List<ASTSCState> sCStates,
			List<ASTSCTransition> sCTransitions, List<ASTSCCode> sCCodes,
			List<ASTSCInternTransition> sCInternTransitions) {
		super(completeness, sCModifier, name, invariant, entryAction, doAction, exitAction, sCStates, sCTransitions,
				sCCodes, sCInternTransitions);
	}

	public Set<ASTSCTransition> getOutgoingTransitions() {
		Set<ASTSCTransition> result = new HashSet<ASTSCTransition>();
		if (getEnclosingScope().isPresent()) {
			Symbol spanning = getEnclosingScope().get().getSpanningSymbol().get();
			if (spanning.getAstNode().get() instanceof ASTStatechart) {
				ASTStatechart sc = (ASTStatechart) spanning.getAstNode().get();
				for (ASTSCTransition t : sc.getSCTransitions()) {
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
