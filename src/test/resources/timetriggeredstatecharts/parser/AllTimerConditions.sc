/* (c) https://github.com/MontiCore/monticore */
statechart AllTimerConditions {
  initial state Waiting;
  state Delayed;
  state Scheduled;
  state Repeated;
  state CalendarMatched;

  Waiting -> Delayed after 10s;
  Waiting -> Scheduled on 2026-07-20T12:00:00Z;
  Waiting -> Repeated every 5min 3 times;
  Waiting -> CalendarMatched cron [*/15 9 * * MON-FRI];
}
