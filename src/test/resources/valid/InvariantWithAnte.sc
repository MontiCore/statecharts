/* (c) https://github.com/MontiCore/monticore */
statechart InvariantWithAnte {
  initial { System.out.println("Ante allowed here"); } state Foo [true];
}