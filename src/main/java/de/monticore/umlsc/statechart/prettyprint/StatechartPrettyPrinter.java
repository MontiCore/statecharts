package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.java.javadsl._ast.ASTJavaBlock;
import de.monticore.java.javadsl._ast.ASTJavaDSLNode;
import de.monticore.java.prettyprint.JavaDSLPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.types.prettyprint.TypesPrettyPrinterConcreteVisitor;
import de.monticore.types.types._ast.ASTReferenceType;
import de.monticore.types.types._ast.ASTTypesNode;
import de.monticore.umlsc.statechart._ast.ASTInvariant;
import de.monticore.umlsc.statechart._ast.ASTSCArguments;
import de.monticore.umlsc.statechart._ast.ASTSCExpressionExt;
import de.monticore.umlsc.statechart._ast.ASTSCInvariantContentExt;
import de.monticore.umlsc.statechart._ast.ASTSCMethodCall;
import de.monticore.umlsc.statechart._ast.ASTSCReturnStatement;
import de.monticore.umlsc.statechart._ast.ASTSCStatementsExt;
import de.monticore.umlsc.statechart._ast.ASTSCTransition;
import de.monticore.umlsc.statechart._ast.ASTSCTransitionBody;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechart._visitor.StatechartVisitor;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCExpression;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCInvariantContent;
import de.monticore.umlsc.statechartwithjava._ast.ASTSCStatements;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaVisitor;

public class StatechartPrettyPrinter implements StatechartVisitor, StatechartWithJavaVisitor {

	private IndentPrinter printer;

	public StatechartPrettyPrinter() {
		this.printer = new IndentPrinter();
	}

	public IndentPrinter getPrinter() {
		return printer;
	}

	public String prettyPrint(ASTStatechartNode node) {
		node.accept(this);
		String result = getPrinter().getContent();
		getPrinter().clearBuffer();
		return result;
	}

	public String prettyPrint(ASTTypesNode node) {
		TypesPrettyPrinterConcreteVisitor v = new TypesPrettyPrinterConcreteVisitor(new IndentPrinter());
		return v.prettyprint(node);
	}

	public String prettyprint(ASTJavaDSLNode node) {
		JavaDSLPrettyPrinter pp = new JavaDSLPrettyPrinter(new IndentPrinter());
		return pp.prettyprint(node);
	}

	@Override
	public void handle(ASTReferenceType ref) {

	}

	@Override
	public void handle(ASTSCTransition node) {
		if (node.stereotypeIsPresent()) {
			node.getStereotype().get().accept(this);
		}
		printer.print(node.getSourceName());
		printer.print(" -> ");
		printer.print(node.getTargetName());
		printer.print(" ");
		if (node.sCTransitionBodyIsPresent()) {
			node.getSCTransitionBody().get().accept(this);
		}
	}

	@Override
	public void handle(ASTSCTransitionBody node) {
		if (node.preConditionIsPresent()) {
			node.getPreCondition().get().accept(this);
		}
		if (node.sCEventIsPresent()) {
			if (node.preConditionIsPresent()) {
				printer.print(" ");
			}
			node.getSCEvent().get().accept(this);
		}
		if (node.sCStatementsIsPresent()) {
			printer.print(" / ");
			node.getSCStatements().get().accept(this);
			if (node.postConditionIsPresent()) {
				node.getPostCondition().get().accept(this);
			}
		}
	}

	@Override
	public void handle(ASTInvariant node) {
		printer.print("[");
		node.getContent().accept(this);
		printer.print("]");
	}

	@Override
	public void handle(ASTSCMethodCall node) {
		printer.print(node.getName().toString());
		if (node.sCArgumentsIsPresent()) {
			node.getSCArguments().get().accept(this);
		}
	}

	@Override
	public void handle(ASTSCReturnStatement node) {
		printer.print("return");
		if (node.getSCExpression().isPresent()) {
			node.getSCExpression().get().accept(this);
		}
	}

	@Override
	public void handle(ASTSCArguments node) {
		printer.print("(");
		for (ASTSCExpressionExt exp : node.getSCExpressions()) {
			exp.accept(this);
		}
		printer.print(")");
	}

	@Override
	public void handle(ASTSCStatementsExt node) {
		if (node instanceof ASTSCStatements) {
			((ASTSCStatements) node).accept(this);
		}
	}

	@Override
	public void handle(ASTSCExpressionExt node) {
		if (node instanceof ASTSCExpression) {
			((ASTSCExpression) node).accept(this);
		}
	}

	@Override
	public void handle(ASTSCInvariantContentExt node) {
		if (node instanceof ASTSCInvariantContent) {
			((ASTSCInvariantContent) node).accept(this);
		}
	}

	@Override
	public void handle(ASTSCStatements node) {
		JavaDSLPrettyPrinter pp = new JavaDSLPrettyPrinter(new IndentPrinter());
		if (node.getBlockStatement() instanceof ASTJavaBlock) {
			ASTJavaBlock b = (ASTJavaBlock) node.getBlockStatement();
			pp.handle(b);
		} else {
			pp.handle(node.getBlockStatement());
		}
		String block = pp.getPrinter().getContent();
		block = block.replaceAll("\\n", "");
		block = block.replaceAll(" ", "");
		printer.print(block);
	}

	@Override
	public void handle(ASTSCExpression node) {
		JavaDSLPrettyPrinter pp = new JavaDSLPrettyPrinter(new IndentPrinter());
		pp.handle(node.getExpression());
		printer.print(pp.getPrinter().getContent());
	}

	@Override
	public void handle(ASTSCInvariantContent node) {
		JavaDSLPrettyPrinter pp = new JavaDSLPrettyPrinter(new IndentPrinter());
		pp.handle(node.getExpression());
		printer.print(pp.getPrinter().getContent());
	}

}
