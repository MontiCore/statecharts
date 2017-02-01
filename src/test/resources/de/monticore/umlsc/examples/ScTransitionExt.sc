package de.monticore.umlsc.parser;

statechart {
	
	state A;
	state B;
	
	A -> B : [JavaExp()] / {somemethod();} [PostCond()];

}