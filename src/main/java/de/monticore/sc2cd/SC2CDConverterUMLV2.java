/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class SC2CDConverterUMLV2  {

  /**
   * Convert a SC to a CD using th StatePattern2 variant:
   *
   * we apply a standard procedure for code generation, by mapping the
   * original model into a class diagram with attributes and methods.
   * The method bodies are kept in specific templates attached to the AST nodes
   * for later evaluation, when producing the sources
   * 
   * @param astscArtifact the SC
   * @param glex
   * @return the CD
   */
  public SC2CDData doConvert(ASTSCArtifact astscArtifact, GlobalExtensionManagement glex) {
    // Phase 1: Work on states
    SC2CDStateVisitorV2 phase1Visitor = new SC2CDStateVisitorV2(glex);
    UMLStatechartsTraverser traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);

    // we use the CD4Code language for the CD (and now switch to it)
    CD4CodeMill.init();

    traverser.handle(astscArtifact);

    // Phase 2: Work with transitions
    SC2CDUMLTransitionVisitor phase2Visitor = new SC2CDUMLTransitionVisitor(phase1Visitor.getScClass(),
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
