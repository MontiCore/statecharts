package de.monticore;

import de.monticore.ast.ASTNode;
import de.monticore.tf.runtime.ODRule;
import de.se_rwth.commons.logging.Log;
import groovy.lang.GroovyShell;
import groovy.lang.MissingMethodException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom Script class for groovy transformation scripts providing helper methods:
 * <p>
 * [nameOfATransformation](ast, dictOfFixedElements=[], doReplace=true) handles transforming ASTs
 * withTransformation acts as a custom alias import for transformation classes.
 */
public abstract class TransformationScript extends groovy.lang.Script {
  protected final Map<String, String> transformationAliases = new HashMap<>();

  /**
   * the groovy shell, which will be used to respect imports
   */
  protected GroovyShell transformationGroovyShell;

  /**
   * Defines an alias for a transformation
   *
   * @param className   the name of the class (either fully qualified or previously imported)
   * @param asTrafoName alias which can be used when calling the method
   */
  @SuppressWarnings("unused")
  protected void withTransformation(String className, String asTrafoName) {
    this.transformationAliases.put(asTrafoName, className);
  }

  /**
   * Acts as a catch-all for any method call.
   * In case no matching method (in the groovy binding or the base class) is found,
   * and the parameters match the parameters specified for a transformation helper method,
   * a new instance of the transformation class is created, fixed elements applied,
   * and finally the pattern matching and replacement operation (unless disabled) done.
   * If the pattern matching was successful, the trafo is returned (allowing access to matched elements).
   * If the pattern matching was not successful, false is returned.
   *
   * @param name  name of the function called (in our case snakeCase of the transformation class)
   * @param argsO [ast, (opt) Map of fixed elements = [], (opt) doReplace=true]
   * @return in case a trafo was applied the trafo is returned, otherwise false
   */
  @Override
  @SuppressWarnings("unused")
  public Object invokeMethod(String name, Object argsO) throws MissingMethodException {
    try {
      // By default, try to invoke a method (or closure in the binding) (using the super method)
      return super.invokeMethod(name, argsO);
    } catch (MissingMethodException mme) {
      // In case no other method was found:
      // Note: We explicitly throw the MissingMethodException the next few lines if the parameters do not match our trafo method syntax
      // To inform the groovy shell about its internal state instead of using Log.error
      if (!(argsO instanceof Object[])) {
        throw mme; // Parameters do not match our trafo method syntax - pass mme back to the groovy shell
      }
      Object[] args = (Object[]) argsO;
      if ((!(args[0] instanceof ASTNode)) || (args.length > 1 && !(args[1] instanceof Map))) {
        throw mme; // Parameters do not match our trafo method syntax - pass mme back to the groovy shell
      }

      Object ast = args[0];
      Map<String, Object> fixed = args.length > 1 ? (Map<String, Object>) args[1] : Collections.emptyMap();
      // Load alias class name, if set
      name = StringUtils.capitalize(name);
      if (this.transformationAliases.containsKey(name)) {
        name = this.transformationAliases.get(name);
      }
      // Initialize new Trafo(ast)
      this.getBinding().setVariable("__trafoTemp", ast);

      ODRule trafo = (ODRule) transformationGroovyShell.evaluate(String.format("new  %s(__trafoTemp)", name));
      // For each fixed element in the dict/map/second parameter:
      this.getBinding().setVariable("__trafoTemp", trafo);
      for (Map.Entry<String, Object> entry : fixed.entrySet()) {
        this.getBinding().setVariable("__trafoTempP", entry.getValue());
        try {
          // Try to call trafo.set_$<key>(value)
          this.transformationGroovyShell.evaluate(String.format("__trafoTemp.set_$%s(__trafoTempP)", entry.getKey()));
        } catch (MissingMethodException innerMME) {
          // Usually the name of the element was misspelled => abort
          // Note: This innerMME should not be passed to the groovy shell, as the set_$ method is the missing method (and not name).
          Log.error("0x712AF02 Failed to set fixed transformation element " + entry.getKey());
        }
      }

      // Do the usual pattern matching and replacement
      if (trafo.doPatternMatching()) {
        if (args.length <= 2 || Boolean.TRUE.equals(args[2])) { // (optional) skip replacement
          trafo.doReplacement();
        }
        return trafo;
      }
      return false;
    }
  }

  // Setter, required
  public void __setTransformationGroovyShell(GroovyShell transformationGroovyShell) {
    this.transformationGroovyShell = transformationGroovyShell;
  }

}
