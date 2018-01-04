/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.java.javadsl._ast.ASTJavaBlock;
import de.monticore.java.javadsl._ast.ASTJavaDSLNode;
import de.monticore.java.prettyprint.JavaDSLPrettyPrinter;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.types.prettyprint.TypesPrettyPrinterConcreteVisitor;
import de.monticore.types.types._ast.ASTReferenceType;
import de.monticore.types.types._ast.ASTTypesNode;
import de.monticore.umlsc.statechart._ast.*;
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
		printer.print("\n");
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
	
	@Override
	public void handle(ASTSCState node){
		node.getSCModifier().accept(this);
		printer.print("state ");
		printer.print(node.getName());
		printer.print("\n");
	}
	
	@Override
	public void handle (ASTSCModifier node) {
		if(node.isInitial()){
			printer.print("initial ");
		}
		if(node.isFinal()){
			printer.print("final ");
		}
		if(node.isLocal()){
			printer.print("local ");
		}
	}
	
	@Override
	public void handle(ASTStatechart node){
		printer.print("statechart ");
		if(node.getName().isPresent()){
			printer.print(node.getName().get());
		}
		printer.print(" {\n");
		printer.indent();
		
		for(ASTSCState s :node.getSCStates()){
			s.accept(this);
		}
		
		for(ASTSCTransition t :node.getSCTransitions()){
			t.accept(this);
		}
		printer.unindent();
		printer.print("}");
	}
	

}
