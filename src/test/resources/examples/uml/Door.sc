/* (c) https://github.com/MontiCore/monticore */
statechart Door {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed close() ;                        // transition with stimulus only
  Closed -> Opened open() / {ringTheDoorBell();};   // transition with stimulus and body
  Closed -> Locked timeOut() / { lockDoor(); } ;
  Locked -> Closed [isAuthorized] unlock() ;        // transition with stimulus and pre condition
}