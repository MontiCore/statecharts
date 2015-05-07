package de.monticore.umlsc.parser;

<<conditions="Java">>
statechart EncryptedDocument {

  initial state WaitForUserEntry {
    entry / {timer.set(5);} [timer.running()];
    exit / {timer.stop();}
    ->: timeout() / {log(timer.getTimeouts() + ". timeout"); timer.set(5);}
  }

  <<encrypted>> state Identification {
    [document.isEncrypted()];
    initial final state sub1;
    state sub2;
    sub1 -> sub2 : [check(passwd)];
    }

  state Failed;

  final state Done;

  <<encrypted>> WaitForUserEntry -> Identification : decrypt(document, passwd);
  WaitForUserEntry -> Failed : [timer.timeouts==3] / {log("Identification expired");}
  Identification -> Failed : [!check(passwd)];
  Identification -> Done : / {document.open();} [document.isDecrypted()];
  Failed -> Done;
}
