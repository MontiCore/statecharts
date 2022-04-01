/* (c) https://github.com/MontiCore/monticore */
package valid;

statechart ValidWithAnte {
  initial { System.out.println("Ante allowed here"); } state Foo;
  state Bar;

  Foo -> Bar;
  Bar -> Foo;
}