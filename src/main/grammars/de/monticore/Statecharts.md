<!-- (c) https://github.com/MontiCore/monticore -->
[[_TOC_]]
# Statecharts

The Statechart language component provides the concrete and abstract syntax to model Statecharts.
It is based on the language components `MCBasicTypes` and `MCCommonLiterals` provided by MontiCore.

## Syntax


A compact teaser for the Statecharts language component:

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

