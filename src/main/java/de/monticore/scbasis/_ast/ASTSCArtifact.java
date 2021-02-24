/* (c) https://github.com/MontiCore/monticore */
package de.monticore.scbasis._ast;

import java.nio.file.Path;

public class ASTSCArtifact extends ASTSCArtifactTOP {
  
  /**
   * Path to the file containing this artifact.
   * Used in CoCos, e.g., for checking whether the package name equals the
   * name of the full qualified name of the folder containing the artifact.
   */
  private Path filePath;
  
  public Path getFilePath() {
    return filePath;
  }
  
  public void setFilePath(Path filePath) {
    this.filePath = filePath;
  }
}
