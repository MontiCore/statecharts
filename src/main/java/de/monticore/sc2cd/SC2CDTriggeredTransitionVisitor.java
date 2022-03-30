/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.TriggeredStatechartsFullPrettyPrinter;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor2;
import de.monticore.triggeredstatecharts._ast.ASTSCEmptyEvent;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsVisitor2;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class SC2CDTriggeredTransitionVisitor extends SC2CDTransitionVisitor
        implements SCTransitions4CodeVisitor2, SCBasisVisitor2, TriggeredStatechartsVisitor2 {


  public SC2CDTriggeredTransitionVisitor(ASTCDClass scClass,
                                         Map<String, ASTCDClass> stateToClassMap,
                                         ASTCDClass stateSuperClass) {
    super(scClass, stateToClassMap, stateSuperClass);
   }

  @Override
  public void visit(ASTSCEmptyEvent node) {
    if (!transition.isPresent() || !transitionBody.isPresent() ) return;

    String stimulus = transition.get().getSourceName() + transition.get().getTargetName();

    //try next numbers
    while (stimuli.contains(stimulus)) {
      if(stimulus.equals(transition.get().getSourceName() + transition.get().getTargetName())){
        stimulus = stimulus + "2";
      }else{
        int number = Integer.parseInt(stimulus.substring(stimulus.length()-1));
        stimulus = stimulus.substring(stimulus.length()-1) + (number+1);
      }
    }

    // Add stimulus method to the Class
    cd4C.addMethod(scClass, "de.monticore.sc2cd.StateStimulusMethod", stimulus, scClass.getName());

    // Add handleStimulus(Class k) method to the StateClass
    stateSuperClass.addCDMember(CD4CodeMill.cDMethodBuilder().setModifier(CDBasisMill.modifierBuilder().build())
      .setMCReturnType(voidReturnType)
      .setName("handle" + StringUtils.capitalize(stimulus)).addCDParameter(
        CD4CodeMill.cDParameterBuilder().setName("k").setMCType(qualifiedType(scClass.getName())).build())
      .build());
    this.stimuli.add(stimulus);

    //needed or the filtering in the next lines cannot be executed
    String s = stimulus;

    //Add handleTransition(Class k) method to the source-state StateClass impl
    if (!this.stateToClassMap.containsKey(this.transition.get().getSourceName())) {
      throw new IllegalStateException("No source state " + this.transition.get().getSourceName() + " found!");
    }
    ASTCDClass stateImplClass = this.stateToClassMap.get(this.transition.get().getSourceName());
    if (stateImplClass.getCDMethodList().stream()
      .anyMatch(x -> x.getName().equals("handle" + StringUtils.capitalize(s)))) {
      // This might occur due to stimuli with arguments
      throw new IllegalStateException("Duplicate transition " + stimulus + " in " + this.transition.get().getSourceName() + " found!");
    }

    // Print the action using the TriggeredStatechartsFullPrettyPrinter
    String action = "/* no action */";
    if (transitionBody.get().isPresentTransitionAction() && transitionBody.get().getTransitionAction()
      .isPresentMCBlockStatement()) {
      IndentPrinter printer = new IndentPrinter();
      new TriggeredStatechartsFullPrettyPrinter(printer).getTraverser().handle(transitionBody.get().getTransitionAction());
      action = printer.getContent();
    }
    // Print the precondition as an expression, too
    String precondition = "true"; // by default true holds
    if (transitionBody.get().isPresentPre()) {
      precondition = this.cd4CodeFullPrettyPrinter.prettyprint(transitionBody.get().getPre());
    }
    // Finally, add the method
    cd4C.addMethod(stateImplClass, "de.monticore.sc2cd.StateClassHandleStimulus", stimulus, scClass.getName(),
      transition.get().getTargetName(), action, precondition);
  }
}
