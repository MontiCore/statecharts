<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Constructor for the main class of a SC

-->
${tc.signature("classname", "states", "initialState")}
${cd4c.constructor("public ${classname}()")}
{
  // TODO: dies sollte sein:
  // $ {glex.defineHookPoint(tc,"<Statement>*StateInitConstructorV2:begin",ast, classname, states, initialState)}
  // es geht aber nur:
  ${glex.defineHookPoint(tc,"<Statement>*StateInitConstructorV2:begin",ast)}

  <#list states as state>
    this.${state?uncap_first} = new ${classname}_${state}();
  </#list>
  <#if initialState??>
    this.state = this.${initialState?uncap_first};
  </#if>
  // TODO: dies sollte sein:
  // $ {glex.defineHookPoint(tc,"<Statement>*StateInitConstructorV2:end",ast, classname, states, initialState)}
  // es geht aber nur:

  ${glex.defineHookPoint(tc,"<Statement>*StateInitConstructorV2:end",ast)}
}
