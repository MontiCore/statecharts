package de.monticore.umlsc.statechart._ast;

import java.util.Optional;

import de.monticore.symboltable.Scope;
import de.monticore.symboltable.Symbol;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;

public class ASTSCTransition extends ASTSCTransitionTOP {

	public ASTSCTransition() {
		super();
	}

	public ASTSCTransition(ASTStereotype stereotype, String sourceName, String targetName,
			ASTSCTransitionBody sCTransitionBody) {
		super(stereotype, sourceName, targetName, sCTransitionBody);
	}

	public ASTSCState getTarget() {
		if (getEnclosingScope().isPresent()) {
			Scope scope = getEnclosingScope().get();
			String name = getTargetName();
			Optional<Symbol> symbol = scope.resolve(name, SCStateSymbol.KIND);
			if (symbol.isPresent()) {
				return (ASTSCState) symbol.get().getAstNode().get();
			}
		}
		return null;
	}

}
