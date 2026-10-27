/* (c) https://github.com/MontiCore/monticore */
statechart MyStatechart {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed;
  Closed -> Opened  / {ringTheDoorBell();}
  Closed -> Locked  / { lockDoor(); }
  Locked -> Closed [isAuthorized];
}
