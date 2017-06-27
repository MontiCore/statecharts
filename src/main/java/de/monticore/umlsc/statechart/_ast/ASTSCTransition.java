package de.monticore.umlsc.statechart._ast;

public class ASTSCTransition extends ASTSCTransitionTOP {

	public ASTSCTransition() {
		super();
	}

	public ASTSCTransition(ASTStereotype stereotype, String sourceName, String targetName,
			ASTSCTransitionBody sCTransitionBody) {
		super(stereotype, sourceName, targetName, sCTransitionBody);
	}

	public ASTSCState getTarget() {
		return null;
	}

}
