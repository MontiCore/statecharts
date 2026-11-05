/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts._parser;

import de.monticore.scbasis._ast.ASTSCArtifact;

import java.nio.file.Paths;
import java.util.Optional;

public class UMLStatechartsParser extends UMLStatechartsParserTOP{
  
  @Override
  public Optional<ASTSCArtifact> parse(String fileName) {
    // use parser as usual and save file path in artifact
    Optional<ASTSCArtifact> sd = parseSCArtifact(fileName);
    sd.ifPresent(e -> e.setFilePath(Paths.get(fileName)));
    return sd;
  }
  
}
