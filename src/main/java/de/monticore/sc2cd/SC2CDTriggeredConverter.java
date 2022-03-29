/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;

public class SC2CDTriggeredConverter {


  /**
   * Convert a SC to a CD
   * @param astscArtifact the SC
   * @return the CD
   */
  public SC2CDData doConvert(ASTSCArtifact astscArtifact, GlobalExtensionManagement glex) {
    // Phase 1: Work on states
    SC2CDStateVisitorV1 phase1Visitor = new SC2CDStateVisitorV1(glex);
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
