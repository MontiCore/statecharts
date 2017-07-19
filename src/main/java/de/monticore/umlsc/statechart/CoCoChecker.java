/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechart;

import de.monticore.umlsc.statechart._cocos.AtLeastOneInitialStateInFinalStatechartChecker;
import de.monticore.umlsc.statechart._cocos.StatechartCoCoChecker;
import de.monticore.umlsc.statechart._cocos.UniqueStateNames;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @since   TODO: add version number
 *
 */
public class CoCoChecker {
	public void start() {
		StatechartCoCoChecker checker = new StatechartCoCoChecker();
		checker.addCoCo(new AtLeastOneInitialStateInFinalStatechartChecker());
		checker.addCoCo(new UniqueStateNames());
		
	}
}
