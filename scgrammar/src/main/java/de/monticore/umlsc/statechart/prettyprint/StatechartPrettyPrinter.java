/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.cd.cd4analysis._ast.ASTCD4AnalysisNode;
//import de.monticore.java.javadsl._ast.ASTJavaBlock;
//import de.monticore.java.javadsl._ast.ASTJavaDSLNode;
//import de.monticore.java.prettyprint.JavaDSLPrettyPrinter;
import de.monticore.expressions.expressionsbasis._ast.ASTExpression;
import de.monticore.javalight._ast.ASTJavaLightNode;
import de.monticore.prettyprint.JavaLightPrettyPrinter;

import de.monticore.prettyprint.IndentPrinter;
import de.monticore.cd.prettyprint.CDPrettyPrinter;
import de.monticore.prettyprint.MCBasicsPrettyPrinter;
//import de.monticore.statements.mccommonstatements._ast.ASTBlockStatement;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
//import de.monticore.types.mcbasictypes._ast.ASTReferenceType;
//import de.monticore.types.mcbasictypes._ast.ASTMCTypesNode;
import de.monticore.types.mcbasictypes._ast.ASTMCBasicTypesNode;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;
import de.monticore.umlsc.statechart._ast.*;
import de.monticore.umlsc.statechart._visitor.StatechartVisitor;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCExpression;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCInvariantContent;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCStatements;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

public class StatechartPrettyPrinter extends MCBasicsPrettyPrinter implements StatechartWithJavaVisitor {

  StatechartPrettyPrinter(IndentPrinter printer) {
    super(printer);
  }

  @Override
  public void setRealThis(StatechartVisitor realThis) {
    super.setRealThis( (StatechartWithJavaVisitor) realThis);
  }

  @Override
  public void setRealThis(StatechartWithJavaVisitor realThis) {
    super.setRealThis(realThis);
  }

  @Override
  public StatechartWithJavaVisitor getRealThis() {
    return (StatechartWithJavaVisitor)super.getRealThis();
  }


  /**
   * @deprecated de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinterDelegator#prettyPrint(ASTStatechartNode)
   * */
  @Deprecated
  public static String prettyPrint(ASTStatechartNode node) {

     //Todo hinweis: nicht new PrettyPrinter sondern PPDelegator nutzen :)
    return new StatechartPrettyPrinterDelegator(new IndentPrinter()).prettyPrint(node);
  }

  @Override
  public void handle(ASTSCStereotype node) {
    getPrinter().print(" << ");
    boolean first = true;
    for (ASTSCStereoValue astStereoValue : node.getValueList()) {
      if (first) {
        astStereoValue.accept(getRealThis());
        first = false;
      } else {
        getPrinter().print(", ");
        astStereoValue.accept(getRealThis());
      }
    }
    getPrinter().print(" >> ");
  }

  @Override
  public void handle(ASTSCStereoValue node) {
    getPrinter().print(node.getName());
    if (node.isPresentValue()) {
      getPrinter().print(" = \"" + node.getValue() + "\"");
    }
  }

  @Override
  public void handle(ASTSCInternTransition node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
      getPrinter().print(" ");
    }
    getPrinter().print("-> ");
    node.getSCTransitionBody().accept(getRealThis());
  }

  @Override
  public void handle(ASTSCTransition node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
    }
    getPrinter().print(node.getSourceName());
    getPrinter().print(" -> ");
    getPrinter().print(node.getTargetName());
    getPrinter().print(" ");
    if (node.isPresentSCTransitionBody()) {
      node.getSCTransitionBody().accept(getRealThis());
    }
    getPrinter().println();
  }

  @Override
  public void handle(ASTSCTransitionBody node) {
    if (node.isPresentPreCondition()) {
      node.getPreCondition().accept(getRealThis());
    }
    if (node.isPresentSCEvent()) {
      if (node.isPresentPreCondition()) {
        getPrinter().print(" ");
      }
      node.getSCEvent().accept(getRealThis());
    }
    getPrinter().print(" / ");
    if (node.isPresentSCStatements()) {
      node.getSCStatements().accept(getRealThis());
      if (node.isPresentPostCondition()) {
        node.getPostCondition().accept(getRealThis());
      }
    }
  }

  @Override
  public void handle(ASTInvariant node) {
    getPrinter().print("[");
    node.getContent().accept(getRealThis());
    getPrinter().print("]");
  }

  @Override
  public void handle(ASTSCMethodCall node) {
    getPrinter().print(node.getName());
    if (node.isPresentSCArguments()) {
      node.getSCArguments().accept(getRealThis());
    }
  }

  @Override
  public void handle(ASTSCReturnStatement node) {
    getPrinter().print("return");
    if (node.isPresentSCExpression()) {
      getPrinter().print("( ");
      node.getSCExpression().accept(getRealThis());
      getPrinter().print(" )");
    }
  }

  @Override
  public void handle(ASTSCArguments node) {
    getPrinter().print("(");
    boolean first = true;
    for (ASTSCExpressionExt exp : node.getSCExpressionList()) {
      if (first) {
        exp.accept(getRealThis());
        first = false;
      } else {
        getPrinter().print(", ");
        exp.accept(getRealThis());
      }
    }
    getPrinter().print(")");
  }

  @Override
  public void handle(ASTSCAction node) {
    if (node.isPresentPreCondition()) {
      node.getPreCondition().accept(getRealThis());
    }
    if (node.isPresentSlash()) {
      getPrinter().print(" / ");
      if (node.isPresentSCStatements()) {
        node.getSCStatements().accept(getRealThis());
      }
      if (node.isPresentPostCondition()) {
        node.getPostCondition().accept(getRealThis());
      }
    }
  }

  @Override
  public void handle(ASTSCStatementsExt node) {
    if (node instanceof ASTSCStatements) {
      ((ASTSCStatements) node).accept(getRealThis());
    }
  }

  @Override
  public void handle(ASTSCExpressionExt node) {
    if (node instanceof ASTSCExpression) {
      ((ASTSCExpression) node).accept(getRealThis());
    }
  }

  @Override
  public void handle(ASTSCInvariantContentExt node) {
    if (node instanceof ASTSCInvariantContent) {
      ((ASTSCInvariantContent) node).accept(getRealThis());
    }
  }

  @Override
  //Todo Für Zeilenumbrüche löschen Rest wieder reinnehmen
  public void handle(ASTSCStatements node) {
      node.getMCBlockStatement().accept(getRealThis());

/*
    StatechartPrettyPrinterDelegator d = new StatechartPrettyPrinterDelegator(new IndentPrinter());
    node.accept(d);

    String block = d.getPrinter().getContent();

    block = block.replaceAll("\\n", "");
    block = block.replaceAll(" ", "");
    getPrinter().print(block);*/
  }

  @Override
  public void handle(ASTSCExpression node) {
    //JavaLightPrettyPrinter pp = new JavaLightPrettyPrinter(new IndentPrinter());
    //node.getExpression().accept(pp);
    node.getExpression().accept(getRealThis());
    //getPrinter().print(pp.getPrinter().getContent());
    //getPrinter().print(new StatechartPrettyPrinterDelegator().prettyprint(node));
  }

  @Override
  public void handle(ASTSCInvariantContent node) {
    //JavaLightPrettyPrinter pp = new JavaLightPrettyPrinter(new IndentPrinter());
    //node.getExpression().accept(pp);
    //getPrinter().print(pp.getPrinter().getContent());
    node.getExpression().accept(getRealThis());
    //getPrinter().print(new StatechartPrettyPrinterDelegator().prettyprint(node));
  }

  @Override
  public void handle(ASTCompleteness node) {
    if (node.isComplete()) {
      getPrinter().print(" (c) ");
    }
    if (node.isIncomplete()) {
      getPrinter().print(" (...) ");
    }
  }

  @Override
  public void handle(ASTSCState node) {
    if (node.isPresentCompleteness()) {
      node.getCompleteness().accept(getRealThis());
    }
    node.getSCModifier().accept(getRealThis());
    getPrinter().print("state " + node.getName());
    if (node.isPresentBracket()) {
      getPrinter().println(" {");
      getPrinter().indent();
      if (node.isPresentInvariant()) {
        node.getInvariant().accept(getRealThis());
      }
      if (node.isPresentEntryAction()) {
        getPrinter().print("entry ");
        node.getEntryAction().accept(getRealThis());
      }
      if (node.isPresentDoAction()) {
        getPrinter().print("do ");
        node.getDoAction().accept(getRealThis());
      }
      if (node.isPresentExitAction()) {
        getPrinter().print("exit");
        node.getExitAction().accept(getRealThis());
      }
      for (ASTSCState astscState : node.getSCStateList()) {
        astscState.accept(getRealThis());
      }
      for(ASTSCTransition astscTransition : node.getSCTransitionList()){
        astscTransition.accept(getRealThis());
      }
      for (ASTSCCode astscCode : node.getSCCodeList()) {
        astscCode.accept(getRealThis());
      }
      for (ASTSCInternTransition astscInternTransition : node.getSCInternTransitionList()) {
        astscInternTransition.accept(getRealThis());
      }
      getPrinter().println();
      getPrinter().unindent();
      getPrinter().print("}");
    }
    getPrinter().println();
  }

  @Override
  public void handle(ASTSCCode node) {
    getPrinter().print("code ");
    node.getSCStatements().accept(getRealThis());
  }

  @Override
  public void handle(ASTSCModifier node) {
    if (node.isPresentStereotype()) {
      node.getStereotype().accept(getRealThis());
    }
    if (node.isInitial()) {
      getPrinter().print("initial ");
    }
    if (node.isFinal()) {
      getPrinter().print("final ");
    }
    if (node.isLocal()) {
      getPrinter().print("local ");
    }
  }

  @Override
  public void handle(ASTStatechart node) {
    if(node.isPresentCompleteness()){
      node.getCompleteness().accept(getRealThis());
    }
    if(node.isPresentStereotype()){
      node.getStereotype().accept(getRealThis());
    }
    getPrinter().print("statechart ");
    if (node.isPresentName()) {
      getPrinter().print(node.getName()+ " ");
    }
    if(node.isPresentClassName()){
      getPrinter().print("for ");
      //MCBasicTypesPrettyPrinter pp = new MCBasicTypesPrettyPrinter(new IndentPrinter());
      node.getClassName().accept(getRealThis());
      //getPrinter().print(pp.prettyprint(node.getClassName()));
    }
    if(node.isPresentSuperSC()){
      getPrinter().print("refines ");
      //MCBasicTypesPrettyPrinter pp = new MCBasicTypesPrettyPrinter(new IndentPrinter());
      //getPrinter().print(pp.prettyprint(node.getSuperSC()));
      node.getSuperSC().accept(getRealThis());
    }
    getPrinter().print("{");
    getPrinter().println();
    getPrinter().indent();

    for (ASTSCState s : node.getSCStateList()) {
      s.accept(getRealThis());
    }
    for (ASTSCTransition t : node.getSCTransitionList()) {
      t.accept(getRealThis());
    }
    getPrinter().unindent();
    getPrinter().println("}");
  }

  @Override
  public void handle(ASTSCArtifact node) {
    if (!node.getPackageList().isEmpty()) {
      getPrinter().print("package ");
      boolean first = true;
      for (String s : node.getPackageList()) {
        if (first) {
          getPrinter().print(s);
          first = false;
        } else {
          getPrinter().print("." + s);
        }
      }
      getPrinter().println(";");
    }
    if (!node.getMCImportStatementList().isEmpty()) {
      for (ASTMCImportStatement importStatement : node.getMCImportStatementList()) {
        getPrinter().print("import ");
        getPrinter().print(importStatement.getQName());
        if(importStatement.isStar()){
          getPrinter().print(".*");
        }
        getPrinter().println(";");
      }
    }
    node.getStatechart().accept(getRealThis());
  }


}
