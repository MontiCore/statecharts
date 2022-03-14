/* (c) https://github.com/MontiCore/monticore */
package valid;

statechart ValidTransitionPrecondition {
  initial state Foo;
  state Bar;

  Foo -> Bar [true || false];
  Bar -> Foo [true && false];
}