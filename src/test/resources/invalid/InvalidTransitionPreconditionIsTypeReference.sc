/* (c) https://github.com/MontiCore/monticore */
package invalid;

statechart InvalidTransitionPreconditionIsTypeReference {
  initial state Foo;
  state Bar;

  Foo -> Bar [String];
}