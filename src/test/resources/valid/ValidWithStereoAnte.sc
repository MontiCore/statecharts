/* (c) https://github.com/MontiCore/monticore */
package valid;

statechart ValidWithStereoAnte {
  <<stereo>> initial { System.out.println("Ante allowed here"); } state Foo;
  state Bar;

  Foo -> Bar;
  Bar -> Foo;
}