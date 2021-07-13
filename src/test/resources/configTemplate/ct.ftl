<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  This template controller overrides the Attribute generating template as an example.

  Call it using the CLI: .. -fp src/test/resources -ct configTemplate/ct.ftl

-->
${tc.signature("glex", "hpService", "scClass", "stateSuperClass", "stateClasses")}

<#-- we override all Attribute templates -->
<#assign hp = hpService.templateHP("configTemplate.CustomAttribute")>
${glex.replaceTemplate("de.monticore.sc2cd.gen.Attribute", hp)}

<#-- We also add our own method to the -->
<#assign superClassHP = hpService.templateHP("configTemplate.CustomStateSuperClass")>
${glex.replaceTemplate("de.monticore.sc2cd.gen.Class", stateSuperClass, superClassHP)}
