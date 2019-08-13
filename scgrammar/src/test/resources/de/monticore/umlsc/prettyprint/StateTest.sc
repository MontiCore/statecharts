/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.prettyprint;

statechart StateTest {
	state A
	initial state SI
	final state SF
	local state SL

	state Outer {
	    state IA
	    state IB
	    IA -> IB
	    -> InnerTrans /
	}
}
