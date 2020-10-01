/* (c) https://github.com/MontiCore/monticore */
statechart NonUnique {
  state Foo {
    state Bar {
      state Foo;
    };
  };

  state Foo;
}