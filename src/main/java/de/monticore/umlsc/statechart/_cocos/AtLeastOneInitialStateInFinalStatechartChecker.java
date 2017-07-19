/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTStatechart;
import de.se_rwth.commons.logging.Log;

/**
 * TODO: Write me!
 *
 * @author (last commit) $Author$
 * @since TODO: add version number
 *
 */
public class AtLeastOneInitialStateInFinalStatechartChecker implements StatechartASTStatechartCoCo {

	/**
	 * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCDefinitionCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCDefinition)
	 */
	@Override
	public void check(ASTStatechart node) {
		if(node.getCompleteness().isPresent() && node.getCompleteness().get().isComplete()) {
			for(ASTSCState s : node.getSCStates()) {
				if(s!=null) {
					return;
				}
			}
			Log.error("no initial State in complete Statechart", node.get_SourcePositionStart());
		}
	}

}
