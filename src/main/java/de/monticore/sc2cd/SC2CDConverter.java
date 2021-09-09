/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.generating.GeneratorSetup;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;

public class SC2CDConverter {

  /**
   * Convert a SC to a CD
   * @param astscArtifact the SC
   * @return the CD
   */
  public SC2CDData doConvertUML(ASTSCArtifact astscArtifact, GeneratorSetup config) {
    CD4C.init(config);
    CD4C.getInstance().setEmptyBodyTemplate("de.monticore.sc2cd.gen.EmptyMethod");

    // Phase 1: Work on states
    SC2CDStateVisitor phase1Visitor = new SC2CDStateVisitor(config.getGlex());
    UMLStatechartsTraverser traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);

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

  /**
   * Convert a SC to a CD
   * @param astscArtifact the SC
   * @return the CD
   */
  public SC2CDData doConvertTriggered(ASTSCArtifact astscArtifact, GeneratorSetup config) {
    CD4C.init(config);
    CD4C.getInstance().setEmptyBodyTemplate("de.monticore.sc2cd.gen.EmptyMethod");

    // Phase 1: Work on states
    SC2CDStateVisitor phase1Visitor = new SC2CDStateVisitor(config.getGlex());
    TriggeredStatechartsTraverser traverser = TriggeredStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);

    CD4CodeMill.init();
    traverser.handle(astscArtifact);

    // Phase 2: Work with transitions
    SC2CDTransitionVisitor phase2Visitor = new SC2CDTransitionVisitor(phase1Visitor.getScClass(),
      phase1Visitor.getStateToClassMap(),
      phase1Visitor.getStateSuperClass());
    traverser = TriggeredStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase2Visitor);
    traverser.add4TriggeredStatecharts(phase2Visitor);
    traverser.add4SCTransitions4Code(phase2Visitor);
    traverser.handle(astscArtifact);

    return new SC2CDData(phase1Visitor.getCdCompilationUnit(), phase1Visitor.getScClass(),
      phase1Visitor.getStateSuperClass(), phase1Visitor.getStateToClassMap().values());
  }
}
