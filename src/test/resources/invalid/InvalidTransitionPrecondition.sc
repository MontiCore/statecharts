/* (c) https://github.com/MontiCore/monticore */
package invalid;

statechart InvalidTransitionPrecondition {
  initial state Foo;
  state Bar;

  Foo -> Bar [4 + 13];
  Bar -> Foo [5 + 3 > 10 ? "yummy" : "urgs"];
}