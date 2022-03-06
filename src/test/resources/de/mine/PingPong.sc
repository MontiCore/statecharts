/* (c) https://github.com/MontiCore/monticore */
package de.mine;

statechart PingPong {
  initial state NoGame;
  state Ping;
  state Pong;

  NoGame -> Ping  startGame() / { count=0; };

  Ping -> NoGame  missBall() / { playerA++; };
  Pong -> NoGame  missBall() / { playerA--; };

  Ping -> Pong    playBall() / { count++; };
  Pong -> Ping    playBall() / { count++; };

}
  
