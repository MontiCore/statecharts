<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java method

-->
${tc.signature("ast", "printer")}
<@compress single_line=true>
    ${printer.prettyprint(ast.getModifier())}
    <#if ast.isIsDefault()>default</#if>
    ${printer.prettyprint(ast.getMCReturnType())}
    ${ast.getName()}(
    <#list ast.getCDParameterList() as param>
        ${printer.prettyprint(param)}<#if param_has_next>, </#if>
    </#list>)
    <#if ast.isPresentCDThrowsDeclaration()>${printer.prettyprint(ast.getCDThrowsDeclaration())}</#if>
</@compress>
${tc.include("de.monticore.sc2cd.gen.EmptyMethod", ast)}