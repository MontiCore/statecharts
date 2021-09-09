/* (c) https://github.com/MontiCore/monticore */
package triggered;

statechart DoorExample2 {
  state Opened;
  initial state Closed;
  state Locked;

  Opened -> Closed ;                        // transition without pre condition and body
  Closed -> Opened / {System.out.println("riiing");} ;   // transition with body
  Closed -> Locked / { System.out.println("Locked"); } ;
  Locked -> Closed [1+1>1] ;        // transition with pre condition
}