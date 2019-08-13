/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.prettyprint;

statechart TransitionTest {
	From -> Target
	From -> Target [PreCond] /
	From -> Target Event /
	From -> Target [PreCond] Event /
	From -> Target / {Statements;}
	From -> Target / {Statements;} [PostCond]
	From -> Target [PreCond] Event / {Statements;} [PostCond]
}
