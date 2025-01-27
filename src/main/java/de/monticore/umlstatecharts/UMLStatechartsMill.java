// (c) https://github.com/MontiCore/monticore
package de.monticore.umlstatecharts;

import de.monticore.umlstatecharts.check.UMLStatechartsTypeCheck;

public class UMLStatechartsMill extends UMLStatechartsMillTOP {

  /** additionally inits the TypeCheck */
  public static void init() {
    UMLStatechartsMillTOP.init();
    UMLStatechartsTypeCheck.init();
  }

}
