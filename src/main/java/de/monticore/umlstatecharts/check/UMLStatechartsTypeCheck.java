/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts.check;

import com.google.common.base.Preconditions;
import de.monticore.expressions.assignmentexpressions.types3.AssignmentExpressionsCTTIVisitor;
import de.monticore.expressions.commonexpressions.types3.CommonExpressionsCTTIVisitor;
import de.monticore.expressions.expressionsbasis.types3.ExpressionBasisTypeVisitor;
import de.monticore.literals.mccommonliterals.types3.MCCommonLiteralsTypeVisitor;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.triggeredstatecharts._visitor.TriggeredStatechartsTraverser;
import de.monticore.triggeredstatecharts.check.TriggeredStatechartsTypeCheck;
import de.monticore.types.mcbasictypes.types3.MCBasicTypesTypeVisitor;
import de.monticore.types3.Type4Ast;
import de.monticore.types3.generics.context.InferenceContext4Ast;
import de.monticore.types3.util.MapBasedTypeCheck3;
import de.monticore.types3.util.WithinScopeBasicSymbolsResolver;
import de.monticore.types3.util.WithinTypeBasicSymbolsResolver;
import de.monticore.visitor.ITraverser;
import de.se_rwth.commons.logging.Log;

/**
 * TypeCheck3 implementation for UMLStatechartsTypeCheck. After calling {@link #init()},
 * this implementation will be available through the TypeCheck3 interface.
 */
public class UMLStatechartsTypeCheck extends TriggeredStatechartsTypeCheck {

  /**
   * @see MapBasedTypeCheck3(ITraverser, Type4Ast, InferenceContext4Ast)
   */
  protected UMLStatechartsTypeCheck(TriggeredStatechartsTraverser typeTraverser,
                                    Type4Ast type4Ast,
                                    InferenceContext4Ast ctx4Ast) {
    super(typeTraverser, type4Ast, ctx4Ast);
  }

  public static void init() {
    Log.trace("Start initializing the TriggeredStatecharts type-check", "UMLStatechartsTypeCheck");
    initTC3Delegate();
    Log.trace("Finished initializing the TriggeredStatecharts type-check", "UMLStatechartsTypeCheck");
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
    Log.trace("Start initializing the UMLStatechartsTypeCheck type-check delegate", "UMLStatechartsTypeCheck");
    initExpressionBasisTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver);
    initMCBasicTypesTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver, inTypeResolver);
    initMCCommonLiteralsTypeVisitor(traverser, type4Ast, ctx4Ast);
    initCommonExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast, inScopeResolver, inTypeResolver);
    initAssignmentExpressionsTypeVisitor(traverser, type4Ast, ctx4Ast);
    Log.trace("Set the UMLStatechartsTypeCheck type-check delegate as global TC3 delegate", "UMLStatechartsTypeCheck");
    setDelegate(new UMLStatechartsTypeCheck(traverser, type4Ast, ctx4Ast));
    Log.trace("Finish initializing the UMLStatechartsTypeCheck type-check delegate", "UMLStatechartsTypeCheck");
  }
}
