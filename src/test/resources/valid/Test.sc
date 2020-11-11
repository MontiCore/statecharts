/* (c) https://github.com/MontiCore/monticore */
statechart {

  event String foo( String a, int b, a.b.Person p) ;
  
  initial state EngineOff;
  state EngineRunning [!fuelIsEmpty] {
    entry / {lightsOn();}
    initial state Parking;
    state Driving;
    exit / {lightsOff(); String foo = b;}
  };
}