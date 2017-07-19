package de.monticore.umlsc.parser;

<<method>>
statechart for WebBidding_login {

  initial state I1
  state Start
  local state A1
  
  state Connected {
    [isConnected()]
  }
  
  state FailedtoConnect
  final state F1
  local state A2
  state WrongLoginOrPassword
  local state F2
  local state A3
  state Done
  final state F3

  I1 -> Start
  Start -> A1 [!isConnected()] / {server.connect();}
  Start -> Connected [isConnected()]
  A1 -> FailedtoConnect [isConnected()]
  FailedtoConnect -> F1 [!isConnected()] / {error("...");}
  A1 -> Connected [isConnected()]
  Connected -> A2 / {server.login(name,password);}
  A2 -> WrongLoginOrPassword return (NOK) / {error("...");}
  WrongLoginOrPassword -> F2
  A2 -> A3 return (OK) / {server.getAuctionList();}
  A3 -> Done return (l) / {setAuctionList(l); setAppStatus(AppStatus.LOGIN_OK);}
  Done -> F3
}
