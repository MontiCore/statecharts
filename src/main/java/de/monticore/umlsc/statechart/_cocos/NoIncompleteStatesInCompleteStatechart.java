/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTStatechart;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @since   TODO: add version number
 *
 */
public class NoIncompleteStatesInCompleteStatechart implements StatechartASTStatechartCoCo {

	/**
	 * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCDefinitionCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCDefinition)
	 */
	@Override
	public void check(ASTStatechart node) {
		if(node.completenessIsPresent() && node.getCompleteness().get().isComplete()) {
			StatechartCoCoChecker c = new StatechartCoCoChecker();
			c.addCoCo(new NoIncompleteStates());
			c.handle(node);
		}
		
	}

}


class NoIncompleteStates implements StatechartASTSCStateCoCo {

	/**
	 * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCStateCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCState)
	 */
	@Override
	public void check(ASTSCState node) {
		// TODO Auto-generated method stub
		
	}
	
}
