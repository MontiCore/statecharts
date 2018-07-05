statechart Stereotype {
  <<moved>> state A {}
  <<moved, ignored>> state B
  <<ignored, moved>> state C
  <<ignored>> state D
  state E
}
