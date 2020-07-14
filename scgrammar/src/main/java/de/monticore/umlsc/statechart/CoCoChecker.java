/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechart;

import de.monticore.umlsc.statechart._cocos.AtLeastOneInitialStateInFinalStatechartChecker;
import de.monticore.umlsc.statechart._cocos.StatechartCoCoChecker;
import de.monticore.umlsc.statechart._cocos.TransitionSourceAndTargetExists;
import de.monticore.umlsc.statechart._cocos.UniqueStateNames;

public class CoCoChecker {
  public void start() {
    StatechartCoCoChecker checker = new StatechartCoCoChecker();
    checker.addCoCo(new AtLeastOneInitialStateInFinalStatechartChecker());
    checker.addCoCo(new TransitionSourceAndTargetExists());
    checker.addCoCo(new UniqueStateNames());
    
  }
}
