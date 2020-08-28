/* (c) https://github.com/MontiCore/monticore */
statechart Door2 {
  initial state Opened;
  state Closed;
  Opened -> Closed close;
}