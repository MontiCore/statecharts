/* (c) https://github.com/MontiCore/monticore */
import java.util.List;
statechart Door1 {
  initial state Opened;
  state Closed;
  Opened -> Closed close;
}