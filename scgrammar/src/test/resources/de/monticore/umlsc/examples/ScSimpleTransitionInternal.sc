/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.examples;

statechart {
  state A {
    -> [JavaExp()] / {somemethod();} [PostCond()]
  }

}
