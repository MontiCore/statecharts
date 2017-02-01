package de.monticore.umlsc.parser;

statechart {
  state A {
    -> [JavaExp()] / {somemethod();} [PostCond()];
  }

}