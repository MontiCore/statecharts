/* (c) https://github.com/MontiCore/monticore */
statechart Door {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed close() ;
  Closed -> Opened open() / {ringTheDoorBell();};
  Closed -> Locked timeOut() / { lockDoor(); } ;
  Locked -> Closed [isAuthorized] unlock() ;
}