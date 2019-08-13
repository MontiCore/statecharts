/* (c) https://github.com/MontiCore/monticore */

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
