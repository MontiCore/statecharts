/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor2;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SC2CDUMLTransitionVisitor extends SC2CDTransitionVisitor
        implements UMLStatechartsVisitor2 {

  public SC2CDUMLTransitionVisitor(ASTCDClass scClass,
                                   Map<String, ASTCDClass> stateToClassMap,
                                   ASTCDClass stateSuperClass) {
    super(scClass, stateToClassMap, stateSuperClass);
  }

  @Override
  public void visit(ASTSCUMLEvent event) {
    String stimulus = event.getName().getQName();
    if (!stimuli.contains(stimulus)) {
      // Add stimulus method to the Class
      cd4C.addMethod(scClass, "de.monticore.sc2cd.StateStimulusMethod", stimulus, scClass.getName());

      // Add handleStimulus(Class k) method to the StateClass
      stateSuperClass.addCDMember(CD4CodeMill.cDMethodBuilder().setModifier(CDBasisMill.modifierBuilder().build())
                                          .setMCReturnType(voidReturnType)
                                          .setName("handle" + StringUtils.capitalize(stimulus)).addCDParameter(
                      CD4CodeMill.cDParameterBuilder().setName("k").setMCType(qualifiedType(scClass.getName())).build())
                                          .build());
      this.stimuli.add(stimulus);
    }

    if (!transition.isPresent() || !transitionBody.isPresent() ) return;

    // Add handleStimulus(Class k) method to the source-state StateClass impl
    if (!this.stateToClassMap.containsKey(this.transition.get().getSourceName())) {
      throw new IllegalStateException("No source state " + this.transition.get().getSourceName() + " found!");
    }
    ASTCDClass stateImplClass = this.stateToClassMap.get(this.transition.get().getSourceName());
    if (stateImplClass.getCDMethodList().stream()
            .anyMatch(x -> x.getName().equals("handle" + StringUtils.capitalize(stimulus)))) {
      // This might occur due to stimuli with arguments
      throw new IllegalStateException("Duplicate transition " + stimulus + " in " + this.transition.get().getSourceName() + " found!");
    }

    // Print the action using the UMLStatechartsFullPrettyPrinter
    String action = "/* no action */";
    if (transitionBody.get().isPresentTransitionAction() && transitionBody.get().getTransitionAction()
            .isPresentMCBlockStatement()) {
      IndentPrinter printer = new IndentPrinter();
      new UMLStatechartsFullPrettyPrinter(printer).getTraverser().handle(transitionBody.get().getTransitionAction());
      action = printer.getContent();
    }
    // Print the precondition as an expression, too
    String precondition = "true"; // by default true holds
    if (transitionBody.get().isPresentPre()) {
      precondition = CD4CodeMill.prettyPrint(transitionBody.get().getPre(), true);
    }
    // Finally, add the method
    cd4C.addMethod(stateImplClass, "de.monticore.sc2cd.StateClassHandleStimulus", stimulus, scClass.getName(),
                   transition.get().getTargetName(), action, precondition);
  }

}
