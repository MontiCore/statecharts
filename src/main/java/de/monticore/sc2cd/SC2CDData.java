/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;

import java.util.Collection;

public class SC2CDData {
  protected final ASTCDCompilationUnit compilationUnit;
  protected final ASTCDClass scClass;
  protected final ASTCDClass stateSuperClass;
  protected final Collection<ASTCDClass> stateClasses;

  public SC2CDData(ASTCDCompilationUnit compilationUnit, ASTCDClass scClass,
                   ASTCDClass stateSuperClass, Collection<ASTCDClass> stateClasses) {
    this.compilationUnit = compilationUnit;
    this.scClass = scClass;
    this.stateSuperClass = stateSuperClass;
    this.stateClasses = stateClasses;
  }

  public ASTCDCompilationUnit getCompilationUnit() {
    return compilationUnit;
  }

  public ASTCDClass getScClass() {
    return scClass;
  }

  public ASTCDClass getStateSuperClass() {
    return stateSuperClass;
  }

  public Collection<ASTCDClass> getStateClasses() {
    return stateClasses;
  }
}
