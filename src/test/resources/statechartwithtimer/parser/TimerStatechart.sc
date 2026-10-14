/* (c) https://github.com/MontiCore/monticore */
statechart TimerStatechart {
  initial state A;
  state B;

  A -> B after 10s;
}
