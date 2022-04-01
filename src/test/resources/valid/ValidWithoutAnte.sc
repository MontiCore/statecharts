/* (c) https://github.com/MontiCore/monticore */
package valid;

statechart ValidWithStereoAnte {
  initial state Foo;
  state Bar;

  Foo -> Bar;
  Bar -> Foo;
}