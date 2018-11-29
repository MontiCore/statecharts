package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.ast.ASTNode;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._ast.ASTSCState;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

public class CountAST implements StatechartWithJavaVisitor {

  public CountAST() {
    this.realThis = this;
  }

  private StatechartWithJavaVisitor realThis;
  public int count = 0;

  @Override
  public void setRealThis(StatechartWithJavaVisitor realThis) {
    this.realThis = realThis;
  }

  @Override
  public StatechartWithJavaVisitor getRealThis() {
    return realThis;
  }


  @Override
  public void visit(ASTNode node) {
    this.count++;
  }

  @Override
  public void visit(ASTSCArtifact node) {
    this.count++;
  }

  @Override
  public void visit(ASTSCState node) {
    this.count++;
  }
}
