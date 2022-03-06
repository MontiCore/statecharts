/* (c) https://github.com/MontiCore/monticore */
package uml;

statechart DoorExample {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed close()   ;               // transition with stimulus only
                                             // transition with stimulus and body
  Closed -> Opened open()    / { System.out.println("riiing"); }; 
  Closed -> Locked timeOut() / { System.out.println("Locked"); } ;
  Locked -> Closed [1+1>1] unlock() ;        // transition with stimulus and simple pre condition
}