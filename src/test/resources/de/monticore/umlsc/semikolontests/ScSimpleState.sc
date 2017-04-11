package de.monticore.umlsc.parser;

statechart for SingleStateClass {
	state A {
		[a>10]
		entry {doEntryStuff();}
		do {doEntryStuff();}
		exit {doEntryStuff();}
		
		state C
		
		//Internal transition
		-> self()
	}
	
	
	state BB
}