/* (c) https://github.com/MontiCore/monticore */
statechart CronStatechart {
  initial state Waiting;
  state Triggered;

  Waiting -> Triggered cron [*/15 9 * * MON-FRI];
}
