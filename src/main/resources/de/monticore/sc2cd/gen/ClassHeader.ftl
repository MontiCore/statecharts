<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class header

-->
${tc.signature("printer", "package")}

<#if package?has_content>
  package ${package?join('.')};
</#if>

${glex.defineHookPoint(tc,"<Import>*gen.ClassHeader",ast)}

<#-- TODO: hier fehlt die standardmaessige Ausgabe notwendiger Imports -->

<@compress single_line=true>
<#--${ast.printAnnotation()}
${ast.printModifier()}-->
    ${printer.prettyprint(ast.getModifier())} class ${ast.getName()}
    <#if ast.isPresentCDExtendUsage()>
        ${printer.prettyprint(ast.getCDExtendUsage())}
    </#if>
    <#if ast.isPresentCDInterfaceUsage()>
        ${printer.prettyprint(ast.getCDInterfaceUsage())}
    </#if>

</@compress>
<#t>
