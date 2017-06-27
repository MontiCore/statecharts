package de.monticore.umlsc.statechart._ast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		return new HashSet<ASTSCTransition>();
	}

}
