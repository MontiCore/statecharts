/* (c) https://github.com/MontiCore/monticore */

// Create new state "A"
addNewState(ast, [newState: "A"])
// Create new state "B"
addNewState(ast, [newState: "B"])

// Create new transition A->B
addNewTransition(ast, [from: "A", to: "B"])

// Create transitions from every state to B
while (m = addNewTransitionFromAny(ast, [to: "B"])) {
  println "Adding transition from " + m.get_$from()
}
