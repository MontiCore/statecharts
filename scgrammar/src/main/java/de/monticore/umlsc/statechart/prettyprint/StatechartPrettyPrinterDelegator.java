package de.monticore.umlsc.statechart.prettyprint;

import de.monticore.MCCommonLiteralsPrettyPrinter;
import de.monticore.cd.cd4analysis._ast.ASTCD4AnalysisNode;
import de.monticore.expressions.prettyprint.AssignmentExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.CommonExpressionsPrettyPrinter;
import de.monticore.expressions.prettyprint.ExpressionsBasisPrettyPrinter;
import de.monticore.javalight._ast.ASTJavaLightNode;
import de.monticore.prettyprint.IndentPrinter;
import de.monticore.prettyprint.JavaLightPrettyPrinter;
import de.monticore.prettyprint.MCBasicsPrettyPrinter;
import de.monticore.types.prettyprint.MCBasicTypesPrettyPrinter;

import de.monticore.statements.prettyprint.MCCommonStatementsPrettyPrinter;
import de.monticore.types.typesymbols._visitor.TypeSymbolsVisitor;
import de.monticore.umlsc.statechart._ast.ASTStatechartNode;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaDelegatorVisitor;

public class StatechartPrettyPrinterDelegator extends StatechartWithJavaDelegatorVisitor{

    protected StatechartWithJavaDelegatorVisitor realThis = this;

    protected de.monticore.prettyprint.IndentPrinter printer;

    public StatechartPrettyPrinterDelegator() {
        this(new IndentPrinter());
    }

    public StatechartPrettyPrinterDelegator(IndentPrinter printer){
        this.printer = printer;




        setJavaLightVisitor(new JavaLightPrettyPrinter(printer));
        setMCCommonLiteralsVisitor(new MCCommonLiteralsPrettyPrinter(printer));
        setMCBasicTypesVisitor(new MCBasicTypesPrettyPrinter(printer));
        setMCBasicsVisitor(new MCBasicsPrettyPrinter(printer));

        setCommonExpressionsVisitor(new CommonExpressionsPrettyPrinter(printer));
        setAssignmentExpressionsVisitor(new AssignmentExpressionsPrettyPrinter(printer));
        setMCCommonStatementsVisitor(new MCCommonStatementsPrettyPrinter(printer));
        setExpressionsBasisVisitor(new ExpressionsBasisPrettyPrinter(printer));


        setStatechartVisitor(new StatechartPrettyPrinter(printer));



    }


    //ToDo noch mehr als aststatechartnode?
    public String prettyprint(ASTStatechartNode node){
        getPrinter().clearBuffer();
        node.accept(getRealThis());
        return getPrinter().getContent();
    }

    //public String prettyprint(ASTCD4AnalysisNode node){
      //  getPrinter().clearBuffer();
        //node.accept(getRealThis());
        //return getPrinter().getContent();
    //}

    public String prettyprint(ASTJavaLightNode node){
        getPrinter().clearBuffer();
        node.accept(getRealThis());
        return getPrinter().getContent();
    }

    public IndentPrinter getPrinter(){
        return printer;
    }

    @Override
    public StatechartWithJavaDelegatorVisitor getRealThis(){
        return realThis;
    }

}
