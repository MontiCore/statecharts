/* (c) https://github.com/MontiCore/monticore */
import java.util.List;
statechart Door1 {
  event java.lang.Integer foo(boolean ringBell, java.lang.String bar); 
  initial state Opened;
  state Closed;
  Opened -> Closed close;
}