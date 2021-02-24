/* (c) https://github.com/MontiCore/monticore */
package de.monticore.triggeredstatecharts._parser;

import de.monticore.scbasis._ast.ASTSCArtifact;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

public class TriggeredStatechartsParser extends TriggeredStatechartsParserTOP {
  
  @Override
  public Optional<ASTSCArtifact> parse(String fileName) throws IOException {
    // use parser as usual and save file path in artifact
    Optional<ASTSCArtifact> sd = parseSCArtifact(fileName);
    sd.ifPresent(e -> e.setFilePath(Paths.get(fileName)));
    return sd;
  }
}
