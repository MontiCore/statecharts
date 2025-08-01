/* (c) https://github.com/MontiCore/monticore */
package triggered;

statechart LoopSystemOut {
  state Hashtag;
  initial state Dashes;

  Dashes -> Hashtag / { System.out.println("#"); }
  Hashtag -> Dashes / { System.out.println("---"); }
}