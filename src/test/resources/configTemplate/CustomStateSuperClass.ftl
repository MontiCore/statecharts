<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Customized
-->
${tc.signature("printer", "package")}


${tc.includeArgs("de.monticore.sc2cd.gen.ClassHeader", [printer, package])}


<#-- add additional method -->
${cd4c.addMethod(ast, "configTemplate.CustomStateSuperClassGetName")}
{

${tc.includeArgs("de.monticore.sc2cd.gen.ClassBody", [printer])}
}