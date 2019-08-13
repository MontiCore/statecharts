/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.examples;

statechart TestInnerTransitions {

	initial final state Offer {
	  -> internal /
	  A -> B
	  C -> D
	  -> internal2 /
	}

}
