package de.monticore.umlsc.parser;

(c) statechart for SingleStateClass {
    
	(...) state A {
	    entry {entry();}
	    do [pre] {do();}
	    exit {exit();} [post]
	    
		-> foo()
	}
	state B {
		entry [pre()] [post]
	}
	
	state C
		
	//Unnötige Trennzeichen entfernt
	A -> B
	
	A -> B  [a<9] event() {foo();} [a<10]
	
	A -> B  event() {a=6;} [a<10]
	
	A -> B  {a=6;}
	
	A -> B  {a=6;} [a<10]
	
	A -> B  [a<9] event()
	
	// alte variante:
	//A -> B : [a<9] event() / {a=6;} [a<10];
	
	//A -> B :  event() / {a=6;} [a<10];
	
	//A -> B : / {a=6;}
	
	//A -> B : / {a=6;} [a<10];
	
	//A -> B : [a<9] event();
	  
}






