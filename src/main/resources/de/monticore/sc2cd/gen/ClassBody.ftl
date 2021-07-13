<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class body (methods, attributes, etc)

-->
${tc.signature("printer")}

<#-- generate all constructors -->
<#list ast.getCDAttributeList() as attr>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Attribute", [attr, printer])}
</#list>
<#-- generate all constructors -->
<#list ast.getCDConstructorList() as constructor>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Constructor", [constructor, printer])}
</#list>
<#-- generate all methods -->
<#list ast.getCDMethodList() as method>
    ${tc.includeArgs("de.monticore.sc2cd.gen.Method", [method, printer])}
</#list>