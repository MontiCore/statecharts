/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.examples;

<<foo>> statechart {
    <<bar>> state A
    <<first="a", second="b">> state B
    <<used>> A -> B
}
