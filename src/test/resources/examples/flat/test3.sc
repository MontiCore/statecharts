/* (c) https://github.com/MontiCore/monticore */
<<test>> statechart Door2 {
  initial state Opened;
  state Closed;
  Opened -> Closed close;
}