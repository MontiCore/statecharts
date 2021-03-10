/* (c) https://github.com/MontiCore/monticore */
statechart Door3 {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed;                        
  Closed -> Opened  / {ringTheDoorBell();};   // transition with body
  Closed -> Locked  / { lockDoor(); } ;
  Locked -> Closed [isAuthorized];            // transition with pre condition
}