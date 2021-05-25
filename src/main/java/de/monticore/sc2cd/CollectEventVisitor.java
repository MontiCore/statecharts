package de.monticore.sc2cd;

import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.sctransitions4code._ast.ASTTransitionBody;
import de.monticore.sctransitions4code._visitor.SCTransitions4CodeVisitor2;
import de.monticore.statements.mcstatementsbasis._ast.ASTMCBlockStatement;
import de.monticore.umlstatecharts._ast.ASTSCUMLEvent;
import de.monticore.umlstatecharts._visitor.UMLStatechartsVisitor2;
import de.se_rwth.commons.logging.Log;

import java.util.*;

public class CollectEventVisitor
        implements SCTransitions4CodeVisitor2, UMLStatechartsVisitor2 {

  private Set<String> stimuli = new HashSet<>();
  private Set<TransitionData> transitionData = new HashSet<>();

  private ASTTransitionBody transitionBody;

  public Set<String> getStimuli() {
    return stimuli;
  }

  @Override
  public void visit(ASTTransitionBody body) {
    this.transitionBody = body;
  }

  @Override
  public void visit(ASTSCUMLEvent event) {
    if (!event.getArguments().getExpressionList().isEmpty()) {
      Log.warn("Skipping stimuli arguments of event " + event.getName().getQName());
    }
    this.stimuli.add(event.getName().getQName());
    this.transitionData.add(
            new TransitionData(event.getName().getQName(),
                               this.transitionBody.isPresentPre() ? Optional.of(this.transitionBody.getPre()) : Optional
                                       .empty(),
                               this.transitionBody.isPresentTransitionAction() ? Optional
                                       .of(this.transitionBody.getTransitionAction().getMCBlockStatement()) : Optional
                                       .empty()
            )
                           );
  }

  public static class TransitionData {
    private final String stimulus;
    private final Optional<ASTExpression> pre;
    private final Optional<ASTMCBlockStatement> ation;

    public TransitionData(String name, Optional<ASTExpression> pre,
                          Optional<ASTMCBlockStatement> ation) {
      this.stimulus = name;
      this.pre = pre;
      this.ation = ation;
    }
  }
}
