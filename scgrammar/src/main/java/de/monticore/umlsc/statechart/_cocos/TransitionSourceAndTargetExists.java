/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart._cocos;

import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechart._ast.ASTSCTransition;
import de.se_rwth.commons.logging.Log;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @since   TODO: add version number
 *
 */
public class TransitionSourceAndTargetExists implements StatechartASTSCTransitionCoCo {

	/**
	 * @see de.monticore.umlsc.statechart._cocos.StatechartASTSCTransitionCoCo#check(de.monticore.umlsc.statechart._ast.ASTSCTransition)
	 */
	@Override
	public void check(ASTSCTransition node) {
		if(node.getTarget().equals(null) || node.getSource().equals(null)){
			Log.error("Source or Target not a State");
		}
	}
}
