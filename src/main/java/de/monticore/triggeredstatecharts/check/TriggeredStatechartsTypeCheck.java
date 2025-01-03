/* (c) https://github.com/MontiCore/monticore */
package de.monticore.triggeredstatecharts.check;

import com.google.common.base.Preconditions;
import de.monticore.expressions.assignmentexpressions.types3.AssignmentExpressionsCTTIVisitor;
import de.monticore.expressions.commonexpressions.types3.CommonExpressionsCTTIVisitor;
import de.monticore.expressions.expressionsbasis.types3.ExpressionBasisTypeVisitor;
import de.monticore.literals.mccommonliterals.types3.MCCommonLiteralsTypeVisitor;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;
import de.monticore.types.mcbasictypes.types3.MCBasicTypesTypeVisitor;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.types3.util.MapBasedTypeCheck3;
import de.monticore.types3.util.WithinScopeBasicSymbolsResolver;
import de.monticore.types3.util.WithinTypeBasicSymbolsResolver;
import de.monticore.visitor.ITraverser;
import de.se_rwth.commons.logging.Log;

/**
 * TypeCheck3 implementation for TriggeredStatecharts. After calling {@link #init()},
 * this implementation will be available through the TypeCheck3 interface.
 */
public class TriggeredStatechartsTypeCheck extends MapBasedTypeCheck3 {

  /**
   * @see MapBasedTypeCheck3(ITraverser, Type4Ast, InferenceContext4Ast)
   */
  protected TriggeredStatechartsTypeCheck(TriggeredStatechartsTraverser typeTraverser,
                                          Type4Ast type4Ast,
                                          InferenceContext4Ast ctx4Ast) {
    super(typeTraverser, type4Ast, ctx4Ast);
  }

  public static void init() {
    Log.trace("Start initializing the TriggeredStatecharts type-check", "TriggeredStatechartsTypeCheck");
    initTC3Delegate();
    Log.trace("Finished initializing the TriggeredStatecharts type-check", "TriggeredStatechartsTypeCheck");
  }

  protected static void initTC3Delegate() {
    initTC3Delegate(
      TriggeredStatechartsMill.inheritanceTraverser(),
      new Type4Ast(),
      new InferenceContext4Ast(),
      new WithinScopeBasicSymbolsResolver(),
      new WithinTypeBasicSymbolsResolver()
    );
  }

  protected static void initTC3Delegate(TriggeredStatechartsTraverser traverser,
                                        Type4Ast type4Ast,
                                        InferenceContext4Ast ctx4Ast,
                                        WithinScopeBasicSymbolsResolver inScopeResolver,
                                        WithinTypeBasicSymbolsResolver inTypeResolver) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Preconditions.checkNotNull(inScopeResolver);
    Preconditions.checkNotNull(inTypeResolver);
    Log.trace("Start initializing the TriggeredStatecharts type-check delegate", "TriggeredStatechartsTypeCheck");
    initExpressionBasisTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver);
    initMCBasicTypesTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver, inTypeResolver);
    initMCCommonLiteralsTypeVisitor(traverser, type4Ast, ctx4Ast);
    initCommonExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver, inTypeResolver);
    initAssignmentExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast);
    Log.trace("Set the TriggeredStatecharts type-check delegate as global TC3 delegate", "TriggeredStatechartsTypeCheck");
    setDelegate(new TriggeredStatechartsTypeCheck(traverser, type4Ast, ctx4Ast));
    Log.trace("Finish initializing the TriggeredStatecharts type-check delegate", "TriggeredStatechartsTypeCheck");
  }


  protected static void initExpressionBasisTypeVisitor(TriggeredStatechartsTraverser traverser,
                                                       Type4Ast type4Ast,
                                                       InferenceContext4Ast ctx4Ast,
                                                       WithinScopeBasicSymbolsResolver inScopeResolver) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Preconditions.checkNotNull(inScopeResolver);
    Log.trace("Start initializing the ExpressionBasis visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    ExpressionBasisTypeVisitor visitor = new ExpressionBasisTypeVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    visitor.setWithinScopeResolver(inScopeResolver);
    traverser.add4ExpressionsBasis(visitor);
    Log.trace("Finish initializing the ExpressionBasis visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initMCBasicTypesTypeVisitor(TriggeredStatechartsTraverser traverser,
                                                    Type4Ast type4Ast,
                                                    InferenceContext4Ast ctx4Ast,
                                                    WithinScopeBasicSymbolsResolver inScopeResolver,
                                                    WithinTypeBasicSymbolsResolver inTypeResolver) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Preconditions.checkNotNull(inScopeResolver);
    Preconditions.checkNotNull(inTypeResolver);
    Log.trace("Start initializing the MCBasicTypes visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    MCBasicTypesTypeVisitor visitor = new MCBasicTypesTypeVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    visitor.setWithinScopeResolver(inScopeResolver);
    visitor.setWithinTypeResolver(inTypeResolver);
    traverser.add4MCBasicTypes(visitor);
    Log.trace("Finish initializing the MCBasicTypes visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initMCCommonLiteralsTypeVisitor(TriggeredStatechartsTraverser traverser,
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

  protected static void initCommonExpressionsTypeVisitor(TriggeredStatechartsTraverser traverser,
                                                         Type4Ast type4Ast,
                                                         InferenceContext4Ast ctx4Ast,
                                                         WithinScopeBasicSymbolsResolver inScopeResolver,
                                                         WithinTypeBasicSymbolsResolver inTypeResolver) {
    Preconditions.checkNotNull(traverser);
    Preconditions.checkNotNull(type4Ast);
    Preconditions.checkNotNull(ctx4Ast);
    Preconditions.checkNotNull(inScopeResolver);
    Preconditions.checkNotNull(inTypeResolver);
    Log.trace("Start initializing the CommonExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
    CommonExpressionsCTTIVisitor visitor = new CommonExpressionsCTTIVisitor();
    visitor.setType4Ast(type4Ast);
    visitor.setContext4Ast(ctx4Ast);
    visitor.setWithinTypeBasicSymbolsResolver(inTypeResolver);
    visitor.setWithinScopeResolver(inScopeResolver);
    traverser.add4CommonExpressions(visitor);
    traverser.setCommonExpressionsHandler(visitor);
    Log.trace("Finish initializing the CommonExpressions visitor of the type-check delegate", "TriggeredStatechartsTypeCheck");
  }

  protected static void initAssignmentExpressionsTypeVisitor(TriggeredStatechartsTraverser traverser,
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
