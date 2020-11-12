/* (c) https://github.com/MontiCore/monticore */
statechart Test2 {

  // event definition with parameters and return type 
  event String bar( String a, int b, a.b.Person p)  ;
  
  // event definition without parameters and return type 
  event  bar2( )  ;
  
  initial state EngineOff;
  state EngineRunning [!fuelIsEmpty] {
    entry / {lightsOn();}
    initial state Parking;
    state Driving;
    exit / {lightsOff(); String foo = b;}
  };
}