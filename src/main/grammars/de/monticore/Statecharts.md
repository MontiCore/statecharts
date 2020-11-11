<!-- (c) https://github.com/MontiCore/monticore -->
[[_TOC_]]

# Statecharts

This project offers a set of language components to define Statechart Languages:
- [SCBasis](SCBasis.mc4),
- [SCStateHierarchy](SCStateHierarchy.mc4),
- [SCStateInvariants](SCStateInvariants.mc4),
- [SCActions](SCActions.mc4),
- [SCDoActions](SCDoActions.mc4),
- [SCTransitions4Code](SCTransitions4Code.mc4),
- [SCTransitions4Modelling](SCTransitions4Modelling.mc4), and
- [SCCompleteness](SCCompleteness.mc4)

And it defines two concrete, complete variants of Statechart languages [UMLStatecharts](UMLStatecharts.mc4) and 
[TriggeredStatecharts](TriggeredStatecharts.mc4) both of which are based on several provided 
language components that offer the different features for Statecharts. 


<div align="center">
<img width="800" src="doc/Statecharts.LFD.png" alt="Statecharts LFD">
<br><b>Figure 1:</b> 
Overview of the statecharts language components and their relations.
</div><br>


## Syntax

A small teaser for the UML Statechart language, which allows a method call as stimulus,
Java expressions as constraints and Java blocks/statements as actions:

```
statechart Door {
  state Opened [9 < now < 18];  // state with invariant
  initial state Closed;
  state Locked;
                                // transitions 
  Opened -> Closed  close() ;
  Closed -> Opened  open()    / { count++; ringTheDoorBell(); };
  Closed -> Locked  timeOut() / lockDoor(); ;
  Locked -> Closed  [isAuthorized() && keyFits()] unlock() ;
}
```

This example models the three states of a door: `Opened`, `Closed`, and `Locked`
and four transitions (each terminated by `;`).
- States may be marked with `initial` and `final`.
- A *transition* is defined by `source -> target` states,
  a *stimulus*, such as a method call `close()`,
  a *trigger condition* `[...]`, and an action `/ ...`.
- *Expressions* can be used for *conditions*, and *statements* respectively blocks `{...}` 
  for the *actions*.

Further language concepts of the UML Statechart are shown in the following example, where state 
`EngineRunning` has two substates as well as *entry*, *exit* *actions* and an *invariant*:

<a name="example2"></a>
``` 
statechart Car {
  initial state EngineOff;
  state EngineRunning [!fuelIsEmpty] {  // state with substates and state invariant (Boolean expression)
    entry / {lightsOn(); }              // entry / exit action
    exit  / {lightsOff();}
    initial state Parking;              // substates
    state Driving;
  };
}
```

Expressions and statements are taken from MontiCores basic grammar library 
and can be extended by any own interesting language constructs 
(such as sending or receiving messages `!m` or `?m`)

 ## UMLStatecharts

 The goal of the `UMLStatecharts` is to provide a language for handling hierarchical Statecharts 
 in the spirit of modeling language for software: Its triggers are usually function calls 
 received either as a normal method call or also as a remote procedure call. 
 Its body contains procedural actions. `UMLStatecharts` are equipped with various enhancements, 
 such as hierarchy, entry and exit states, conditions on the transitions or invariants.
 However, they on purpose do not provide the full original Statechart language, because 
 in an object oriented development process, neither history, nor AND-states are really needed.
 See [Rum16] for a detailed discussion. 

 The `UMLStatecharts` language combines the language components `SCActions`, `SCDoActions`, `SCStateHierarchy`, 
 `SCStateInvariants`, `SCCompleteness`, `SCTransitions4Modelling`, `CommonExpressions`, SCEvents, and
 `MCCommonStatements`. The teasers above conform to this language.
 
 ## TriggeredStatecharts
 
 `TriggeredStatecharts` is a second combination of the basic language components. 
 It combines the 
 language components `SCActions`, `SCStateHierarchy`, `SCTransitions4Code`, `CommonExpressions` and 
 `MCCommonStatements`. Thus, it allows modeling Statecharts with hierarchical states that may 
 have entry and exit actions.
 
 On the one hand, `TriggeredStatecharts` show how flexible the language component library
 is and how easy individual languages can be designed.
 
 On the other hand, `TriggeredStatecharts` have their own dedicated use for descriptions
 in the embedded area, where a software component is often *triggered* 
 on a regular basis (i.e. with a certain periodic repetition and signals do not
 arrive as events, but as available values). We also designed `TriggeredStatecharts`
 as implementation oriented language, and therefore do not add specification oriented constructs:
 - no do actions 
 - no state invariants
 - transitions consist of a precondition, an event and an action, each of which is optional.

 
 ## SCBasis
`SCBasis` is the basic grammar component for automata. It defines
a Statechart structure including package 
and imports as well as the Statechart nonterminal itself. 
Inside a Statechart the `SCArtifact` extension point allows adding various forms
of states and transitions (or other constructs).
Furthermore, states and transitions 
with extension points for their bodies (`SCSBody` and `SCTBody`) are provided. 

 ## Hierarchical states
 This project provides two language components for hierarchical states: `SCStateHierarchy` and 
 `SCStateInvariants`. The language component `SCStateHierarchy` is based on the `SCBasis` language 
 component and adds a state body variant that allows nested state elements, i.e., states and transitions.
 The second language component `SCStateInvariants` extends `SCStateHierarchy` and additionally provides 
 invariants within states. The [second syntax example](#example2) contains an hierachical state 
 `EngineRunning` which has two substates `Parking` and `Driving` and an invariant `[!fuelIsEmpty]`.
 
 ## Actions
 
 ## Transitions
 
 <!-- TODO
 ## SCActions
 ## SCDoActions
 ## SCTransitions4Code
 ## SCTransitions4Modelling
 ## SCCompleteness
 -->

<!-- #### Handwritten Extensions -->

<!-- ### Symboltable -->

## Symboltable

### Symbol kinds used by the SC language (importable or subclassed):
The SC language uses symbols of kind `TypeSymbol`, `VariableSymbol` as well as 
the symbols that are used by these symbols (e.g. `FunctionSymbol` 
is used by `TypeSymbol`.

Furthermore, the SC language reuses the `DiagramSymbol` of the BasicSymbols language component for named Statecharts.


### Symbol kinds defined by the SD language (exported):
The SC language defines the symbol kinds `SCStateSynbol` and `SCEventDefSymbol`.      
- A `SCStateSymbol` is defined as (and has no additional body):
  ```
  class SCStateSymbol {
      String name;
  }
  ```
- The UMLStatechart language also exports the kind `SCEventDefSymbol` defined as:
  ```
  class SCEventDefSymbol {
      String name;
  }
  ```
  
  This kind of symbols are refered to by stimuli that do not refer to functions 
  and thus do not have arguments and return types.
  Symbols of this kind are not produced by Statecharts but instead used by the events of transitions.

### Symbols imported by SC models:
* SCs import `VariableSymbols`, `FunctionSymbols` and `TypeSymbols`. 
These imported symbol can be used within action or transition bodies. 
Furthermore, events of transitions can use `FunctionSymbols` or `SCEventDefSymbols`,
 invariants and pre-/post-conditions can use `VariableSymbols` and `FunctionSymbols`.

### Symbols exported by SC models:
* SC models export `SCStateSymbols`. 
For each state defined in an SC, the SC exports a corresponding `SCStateSymbol`. 
* Each SC exports at most one `DiagramSymbol` corresponding to the Statechart.
- The artifact scope of an SC "Door.sc" is stored in "Door.scsym".
  Structure:
  ```
  {
    "name": "Door",
    "symbols": [
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Opened"
      },
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Closed"
      },
      {
        "kind": "de.monticore.scbasis._symboltable.SCStateSymbol",
        "name": "Locked"
      },
      {
        "kind": "de.monticore.symbols.basicsymbols._symboltable.DiagramSymbol",
        "name": "Door"
      }
    ]
  }
  ```


## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)

* [**List of languages**](https://github.com/MontiCore/monticore/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Best Practices](https://github.com/MontiCore/monticore/blob/dev/docs/BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)

* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)

