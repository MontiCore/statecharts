// (c) https://github.com/MontiCore/monticore
package de.monticore.triggeredstatecharts;

import de.monticore.triggeredstatecharts.check.TriggeredStatechartsTypeCheck;

public class TriggeredStatechartsMill extends TriggeredStatechartsMillTOP {

  /** additionally inits the TypeCheck */
  public static void init() {
    TriggeredStatechartsMillTOP.init();
    TriggeredStatechartsTypeCheck.init();
  }

}
