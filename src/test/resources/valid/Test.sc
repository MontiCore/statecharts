/* (c) https://github.com/MontiCore/monticore */
statechart Test {
  initial state EngineOff;
  state EngineRunning {
    [!fuelIsEmpty]
    entry / {lightsOn();}
    initial state Parking;
    state Driving;
    exit / {lightsOff(); String foo = b;}
  };
}