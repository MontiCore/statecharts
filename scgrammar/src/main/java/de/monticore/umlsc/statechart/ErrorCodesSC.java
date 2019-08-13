/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechart;

/**
 * Created by eikermann on 23.06.2017.
 */
public enum ErrorCodesSC {

  FileName("0xA0000","The grammar name [grammarName] must not differ from the file name of the grammar (without its file extension)."),
  PackageName("0xA0001","The grammar package %s must not differ from the path of the grammar file.");






  String id;
  String description;
  ErrorCodesSC(String id, String desc) {
    this.id = id;
    this.description = desc;
  }
  public String toString() {
    return this.id + " "+ this.description;
  }
}
