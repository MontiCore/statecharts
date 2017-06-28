package de.monticore.umlsc.cocos;

statechart Buchungen for Konto {
  state Eingabe {
    entry [e.entryCond()] / {entryAction();} 
  }
  state Angewiesen


  state Abgeschlossen
  
  Eingabe -> Angewiesen [a.pre()>4] event() / {action();} [a.post()<7]

}
