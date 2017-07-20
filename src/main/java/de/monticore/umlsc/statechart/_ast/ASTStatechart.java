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

import java.util.List;

import de.monticore.types.types._ast.ASTReferenceType;

public class ASTStatechart extends ASTStatechartTOP {

	public ASTStatechart() {
		super();
	}

	public ASTStatechart(ASTCompleteness completeness, ASTStereotype stereotype, String name,
			ASTReferenceType className, ASTReferenceType superSC, List<ASTSCState> sCStates,
			List<ASTSCTransition> sCTransitions) {
		super(completeness, stereotype, name, className, superSC, sCStates, sCTransitions);
	}

	public ASTSCState getInitialState() {
		// Search for a state marked as initial
		for (ASTSCState s : getSCStates()) {
			for (ASTSCStateModifierEnum mod : s.getSCModifier().getSCStateModifierEnums()) {
				if (mod.equals(ASTSCStateModifierEnum.INITIAL)) {
					return s;
				}
			}
		}
		// Assert there is a state
		if (getSCStates().isEmpty()) {
			return null;
		}
		// Default: First state
		return getSCStates().get(0);
	}

}
