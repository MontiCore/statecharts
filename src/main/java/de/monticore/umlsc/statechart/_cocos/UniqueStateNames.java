/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechart._cocos;

import java.util.ArrayList;
import java.util.Collection;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.se_rwth.commons.logging.Log;

/**
 * TODO: Write me!
 *
 * @author (last commit) $Author$
 * @since TODO: add version number
 *
 */
public class UniqueStateNames implements StatechartASTSCStateCoCo {

	Collection<String> stateNames = new ArrayList<>();

	/**
	 * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCStateCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCState)
	 */
	@Override
	public void check(ASTSCState node) {
		if (stateNames.contains(node.getName())) {
			Log.error(String.format("State name %s must be unique", node.getName()), node.get_SourcePositionStart());
		} else {
			this.stateNames.add(node.getName());
		}

	}

}
