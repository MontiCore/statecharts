package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.*;
import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor2;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.types.mcbasictypes._ast.ASTMCReturnType;
import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor2;
import de.se_rwth.commons.Splitters;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SC2CDTransitionVisitor
        implements UMLStatechartsVisitor2, SCTransitions4CodeVisitor2, SCBasisVisitor2 {

  /**
   * The main class
   */
  private final ASTCDClass scClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  private final Map<String, ASTCDClass> stateToClassMap;


  private final ASTMCReturnType voidReturnType;
  private final ASTCDClass stateSuperClass;
  private final CD4C cd4C;


  private final List<String> stimuli = new ArrayList<>();


  public SC2CDTransitionVisitor(ASTCDClass scClass,
                                Map<String, ASTCDClass> stateToClassMap,
                                ASTCDClass stateSuperClass,
                                CD4C cd4C) {
    this.scClass = scClass;
    this.stateToClassMap = stateToClassMap;
    this.stateSuperClass = stateSuperClass;
    this.cd4C = cd4C;

    voidReturnType = CDBasisMill.mCReturnTypeBuilder().setMCVoidType(CDBasisMill.mCVoidTypeBuilder().build()).build();
  }


  private ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }

  private ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
            .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }


  private ASTSCTransition transition;
  private ASTTransitionBody transitionBody;

  @Override
  public void visit(ASTSCTransition node) {
    this.transition = node;
  }

  @Override
  public void visit(ASTTransitionBody node) {
    this.transitionBody = node;
  }

  @Override
  public void endVisit(ASTSCTransition node) {
    this.transition = null;
  }

  @Override
  public void endVisit(ASTTransitionBody node) {
    this.transitionBody = null;
  }

  @Override
  public void visit(ASTSCUMLEvent event) {
    String stimulus = event.getName().getQName();
    if (!stimuli.contains(stimulus)) {
      // Add stimulus method to the Class
      cd4C.addMethod(scClass, "sc2cd.StateStimulusMethod", stimulus);

      // Add handleStimulus(Class k) method to the StateClass
      stateSuperClass.addCDMember(CD4CodeMill.cDMethodBuilder().setModifier(CDBasisMill.modifierBuilder().build())
                                          .setMCReturnType(voidReturnType)
                                          .setName("handle" + StringUtils.capitalize(stimulus)).addCDParameter(
                      CD4CodeMill.cDParameterBuilder().setName("k").setMCType(qualifiedType(scClass.getName())).build())
                                          .build());
      this.stimuli.add(stimulus);
    }

    if (transition == null || transitionBody == null) return;

    // Add handleStimulus(Class k) method to the source-state StateClass impl
    ASTCDClass stateImplClass = this.stateToClassMap.get(this.transition.getSourceName());
    if (stateImplClass == null) {
      throw new IllegalStateException("No source state " + this.transition.getSourceName() + " found!");
    }
    // TODO: action & preconditions
    cd4C.addMethod(stateImplClass, "sc2cd.StateClassHandleStimulus", stimulus, scClass.getName(),
                   transition.getTargetName());
  }


}
