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
