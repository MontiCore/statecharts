;
statechart Door1 {
  initial state Opened;
  state Closed;
  Opened -> Closed close;
}