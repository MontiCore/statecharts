/* (c) https://github.com/MontiCore/monticore */
statechart Test2 {

  event String bar( String a, int b, a.b.Person p)  ;
  event  bar2( )  ;
  event bar3;
  
  initial state EngineOff;
  state EngineRunning [!fuelIsEmpty] {
    entry / {lightsOn();}
    initial state Parking;
    state Driving;
    exit / {lightsOff(); String foo = b;}
  };
}