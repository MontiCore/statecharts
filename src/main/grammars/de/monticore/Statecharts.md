<!-- (c) https://github.com/MontiCore/monticore -->
[[_TOC_]]
# Statecharts
This project offers two variants of Statechart Languages [UMLStatecharts](UMLStatecharts.mc4) and 
[TriggeredStatecharts](TriggeredStatecharts.mc4) both of which are based on several provided 
language components that offer the different features for Statecharts. These components are:
[SCBasis](SCBasis.mc4)
[SCStateHierarchy](SCStateHierarchy.mc4)
[SCStateInvariants](SCStateInvariants.mc4)
[SCActions](SCActions.mc4)
[SCDoActions](SCDoActions.mc4)
[SCTransitions4Code](SCTransitions4Code.mc4)
[SCTransitions4Modelling](SCTransitions4Modelling.mc4)
[SCCompleteness](SCCompleteness.mc4)

## Syntax


A compact teaser for the UML Statecharts language:

```
statechart Door {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed close() ;
  Closed -> Opened open() / {ringTheDoorBell();};
  Closed -> Locked timeOut() / { lockDoor(); } ;
  Locked -> Closed [isAuthorized] unlock() ;
}
```
This example models the different states of a door: `Opened`, `Closed`, and `Locked`.
When the statechart is in state `Opened`, it is possible to close the door using `close()`.
When the statechart is in state `Closed`, it is possible to open the door using `open()`. 
In the latter case the action  `ringDoorBell()` is executed. 
When the door is `Closed` it is automatically locked after some time due to a 
`timeout()` event that triggers the `lockDoor()` action.
In case the door is locked, it can be unlocked by using `unlock()` if the pre-condition
 `isAuthorized` is fulfilled.
 
 ## UMLStatecharts
 This language combines the language components SCActions, SCDoActions, SCStateHierarchy, 
 SCStateInvariants, SCCompleteness, SCTransitions4Modelling, CommonExpressions and
 MCCommonStatements. Thus, it allows modeling UML-like Statechart with hierarchical states that 
 may have entry, do and exit actions as well as invariants. Furthermore, transitions with pre- 
 and postconditions, events and actions are possible. The  statechart itself as well as states 
 can be marked as complete. The teaser above conforms to this language.
 
 ## TriggeredStatecharts
 The second variant of statecharts offered are TriggeredStatecharts. This language combines the 
 language components SCActions, SCStateHierarchy, SCTransitions4Code, CommonExpressions and 
 MCCommonStatements. Thus, it allows modeling statechart with hierarchical states that may 
 have entry and exit actions but neither do actions nor invariants. Furthermore, transitions
 can only consist of  a precondition, an event and an action.
 
 ## SCBasis
SCBasis is the most basic grammar component provided. It offers the SCArtifact including package 
and imports as well as the Statechart nonterminal itself. Furthermore, states and transitions 
with extension points for their bodies (SCSBody and SCTBody) are provided. In addition, the 
interfaces StateElement and StatechartElement can be used as extension points to add further 
features for statecharts or states.
 
 <!-- TODO
 ## SCStateHierarchy
 ## SCStateInvariants
 ## SCActions
 ## SCDoActions
 ## SCTransitions4Code
 ## SCTransitions4Modelling
 ## SCCompleteness
 -->

<!-- #### Handwritten Extensions -->

<!-- ### Symboltable -->


## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)

* [**List of languages**](https://git.rwth-aachen.de/monticore/monticore/-/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://git.rwth-aachen.de/monticore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Best Practices](BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)

* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)

