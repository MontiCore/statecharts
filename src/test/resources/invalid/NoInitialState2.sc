/* (c) https://github.com/MontiCore/monticore */
statechart NoInitialState2 {
  state EngineOff;              
  state EngineRunning {  
    initial state Parking;            
    state Driving;
  };
}