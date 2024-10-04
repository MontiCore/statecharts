/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.scbasis._ast.ASTSCTransition;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor2;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.types.mcbasictypes._ast.ASTMCReturnType;
import de.se_rwth.commons.Splitters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

abstract public class SC2CDTransitionVisitor
        implements  SCTransitions4CodeVisitor2, SCBasisVisitor2 {

  /**
   * The main class
   */
  protected final ASTCDClass scClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> stateToClassMap;

  protected final ASTMCReturnType voidReturnType;

  protected final ASTCDClass stateSuperClass;
  protected final CD4C cd4C;


  protected final List<String> stimuli = new ArrayList<>();


  public SC2CDTransitionVisitor(ASTCDClass scClass,
                                Map<String, ASTCDClass> stateToClassMap,
                                ASTCDClass stateSuperClass) {
    this.scClass = scClass;
    this.stateToClassMap = stateToClassMap;
    this.stateSuperClass = stateSuperClass;
    this.cd4C = CD4C.getInstance();

    this.voidReturnType = CDBasisMill.mCReturnTypeBuilder().setMCVoidType(CDBasisMill.mCVoidTypeBuilder().build()).build();
  }


  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }

  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
            .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }


  protected Optional<ASTSCTransition> transition = Optional.empty();
  protected Optional<ASTTransitionBody> transitionBody = Optional.empty();

  @Override
  public void visit(ASTSCTransition node) {
    this.transition = Optional.of(node);
  }

  @Override
  public void visit(ASTTransitionBody node) {
    this.transitionBody = Optional.of(node);
  }

  @Override
  public void endVisit(ASTSCTransition node) {
    this.transition = Optional.empty();
  }

  @Override
  public void endVisit(ASTTransitionBody node) {
    this.transitionBody = Optional.empty();
  }

}
