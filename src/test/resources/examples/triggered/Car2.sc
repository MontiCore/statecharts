/* (c) https://github.com/MontiCore/monticore */
statechart Car2 {
  initial state EngineOff;              // state with initial marker
  state EngineRunning  {                // state with substates
    entry / {lightsOn();}               // entry action
    initial state Parking;              // substate
    state Driving;
    exit / {lightsOff();}               // exit action
  };
  
  EngineOff -> EngineRunning;
  EngineRunning -> EngineOff;
}