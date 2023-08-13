/* (c) https://github.com/MontiCore/monticore */
package invalid;

statechart TransitionPreconditionIsObscure {
  initial state Foo;
  state Bar;

  Foo -> Bar [thisVarIsNotPresent];
}