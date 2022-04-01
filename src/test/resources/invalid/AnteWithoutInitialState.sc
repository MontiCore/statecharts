/* (c) https://github.com/MontiCore/monticore */

statechart AnteWithoutInitialState {
  initial state Foo;

  { System.out.println("Uh oh not initial"); } state Bar1;
  final { System.out.println("Not initial either"); } state Bar2;
  <<stereo>>
    { System.out.println("Still not initial"); }
    state Bar3;
  <<stereo>> final
    { System.out.println("Combination does not make it initial either"); }
    state Bar4;

  Foo -> Bar1;
  Bar1 -> Bar2;
  Bar2 -> Bar3;
  Bar3 -> Bar4;
  Bar4 -> Foo;
}