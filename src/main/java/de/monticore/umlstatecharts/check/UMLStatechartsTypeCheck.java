/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts.check;

import com.google.common.base.Preconditions;
import de.monticore.expressions.assignmentexpressions.types3.AssignmentExpressionsCTTIVisitor;
import de.monticore.expressions.commonexpressions.types3.CommonExpressionsCTTIVisitor;
import de.monticore.expressions.expressionsbasis.types3.ExpressionBasisTypeVisitor;
import de.monticore.literals.mccommonliterals.types3.MCCommonLiteralsTypeVisitor;
import de.monticore.types.mcbasictypes.types3.MCBasicTypesTypeVisitor;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.types3.util.MapBasedTypeCheck3;
import de.monticore.types3.util.WithinScopeBasicSymbolsResolver;
import de.monticore.types3.util.WithinTypeBasicSymbolsResolver;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.monticore.visitor.ITraverser;
import de.se_rwth.commons.logging.Log;

/**
 * TypeCheck3 implementation for UMLStatechartsTypeCheck. After calling {@link #init()},
 * this implementation will be available through the TypeCheck3 interface.
 */
public class UMLStatechartsTypeCheck extends MapBasedTypeCheck3 {

  /**
   * @see MapBasedTypeCheck3(ITraverser, Type4Ast, InferenceContext4Ast)
   */
  protected UMLStatechartsTypeCheck(UMLStatechartsTraverser typeTraverser,
                                    Type4Ast type4Ast,
                                    InferenceContext4Ast ctx4Ast) {
    super(typeTraverser, type4Ast, ctx4Ast);
  }

  public static void init() {
    Log.trace("Start initializing the UMLStatecharts type-check", "UMLStatechartsTypeCheck");
    initTC3Delegate();
    Log.trace("Finished initializing the UMLStatecharts type-check", "UMLStatechartsTypeCheck");
  }

  protected static void initTC3Delegate() {
    initTC3Delegate(
      UMLStatechartsMill.inheritanceTraverser(),
      new Type4Ast(),
      new InferenceContext4Ast()
    );
  }

  protected static void initTC3Delegate(UMLStatechartsTraverser traverser,
                                        Type4Ast type4Ast,
                                        InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Log.trace("Start initializing the UMLStatechartsTypeCheck type-check delegate", "UMLStatechartsTypeCheck");
    WithinScopeBasicSymbolsResolver.init();
    WithinTypeBasicSymbolsResolver.init();
    initExpressionBasisTypeVisitor(traverser, type4Ast, ctx4Ast);
    initMCBasicTypesTypeVisitor(traverser, type4Ast, ctx4Ast);
    initMCCommonLiteralsTypeVisitor(traverser, type4Ast, ctx4Ast);
    initCommonExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast);
    initAssignmentExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast);
    Log.trace("Set the UMLStatechartsTypeCheck type-check delegate as global TC3 delegate", "UMLStatechartsTypeCheck");
    setDelegate(new UMLStatechartsTypeCheck(traverser, type4Ast, ctx4Ast));
    Log.trace("Finish initializing the UMLStatechartsTypeCheck type-check delegate", "UMLStatechartsTypeCheck");
  }

  protected static void initExpressionBasisTypeVisitor(UMLStatechartsTraverser traverser,
                                                       Type4Ast type4Ast,
                                                       InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Log.trace("Start initializing the ExpressionBasis visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    ExpressionBasisTypeVisitor visitor = new ExpressionBasisTypeVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    traverser.add4ExpressionsBasis(visitor);
    Log.trace("Finish initializing the ExpressionBasis visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initMCBasicTypesTypeVisitor(UMLStatechartsTraverser traverser,
                                                    Type4Ast type4Ast,
                                                    InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Log.trace("Start initializing the MCBasicTypes visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    MCBasicTypesTypeVisitor visitor = new MCBasicTypesTypeVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    traverser.add4MCBasicTypes(visitor);
    Log.trace("Finish initializing the MCBasicTypes visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initMCCommonLiteralsTypeVisitor(UMLStatechartsTraverser traverser,
                                                        Type4Ast type4Ast,
                                                        InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    MCCommonLiteralsTypeVisitor visitor = new MCCommonLiteralsTypeVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    traverser.add4MCCommonLiterals(visitor);
  }

  protected static void initCommonExpressionsTypeVisitor(UMLStatechartsTraverser traverser,
                                                         Type4Ast type4Ast,
                                                         InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Log.trace("Start initializing the CommonExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    CommonExpressionsCTTIVisitor visitor = new CommonExpressionsCTTIVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    traverser.add4CommonExpressions(visitor);
    traverser.setCommonExpressionsHandler(visitor);
    Log.trace("Finish initializing the CommonExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initAssignmentExpressionsTypeVisitor(UMLStatechartsTraverser traverser,
                                                             Type4Ast type4Ast,
                                                             InferenceContext4Ast ctx4Ast) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Log.trace("Start initializing the AssignmentExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    AssignmentExpressionsCTTIVisitor visitor = new AssignmentExpressionsCTTIVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    traverser.add4AssignmentExpressions(visitor);
    traverser.setAssignmentExpressionsHandler(visitor);
    Log.trace("Finish initializing the AssignmentExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }
}
