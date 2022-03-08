/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.generating.GeneratorSetup;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class SC2CDConverterUMLV2 {

  /**
   * Convert a SC to a CD using th StatePattern2 variant:
   *
   * we apply a standard procedure for code generation, by mapping the
   * original model into a class diagram with attributes and methods.
   * The method bodies are kept in specific templates attached to the AST nodes
   * for later evaluation, when producing the sources
   * 
   * @param astscArtifact the SC
   * @param config a valid GeneratorSetup, where the respective templates are stored 
   * @return the CD
   */
  public SC2CDData doConvertUMLV2(ASTSCArtifact astscArtifact, GeneratorSetup config) {
    // initialize the cd4c object 
    CD4C.init(config);
    
    // what to do, when no specific method body is given:
    // this could also contain a warning on execution, or uncompilable code
    CD4C.getInstance().setEmptyBodyTemplate("de.monticore.sc2cd.gen.EmptyMethod");

    // Phase 1: Work on states
    SC2CDStateVisitor phase1Visitor = new SC2CDStateVisitor(config.getGlex(),
                                            "de.monticore.sc2cd.StateInitConstructorV2",
                                            "de.monticore.sc2cd.MainClassV2");
    UMLStatechartsTraverser traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);

    // we use the CD4Code language for the CD (and now switch to it)
    CD4CodeMill.init();

    traverser.handle(astscArtifact);

    // Phase 2: Work with transitions
    SC2CDTransitionVisitor phase2Visitor = new SC2CDTransitionVisitor(phase1Visitor.getScClass(),
                                                                      phase1Visitor.getStateToClassMap(),
                                                                      phase1Visitor.getStateSuperClass());
    traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase2Visitor);
    traverser.add4UMLStatecharts(phase2Visitor);
    traverser.add4SCTransitions4Code(phase2Visitor);
    traverser.handle(astscArtifact);

    // voila
    return new SC2CDData(phase1Visitor.getCdCompilationUnit(), phase1Visitor.getScClass(),
                         phase1Visitor.getStateSuperClass(),
                         phase1Visitor.getStateToClassMap().values());
  }

}
