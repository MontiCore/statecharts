<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  This template controller overrides the Attribute generating template as an example.

  Call it using the CLI: .. -fp src/test/resources -ct configTemplate/ct.ftl

-->
${tc.signature("glex", "converter", "hwPath", "generator")}

<#assign cdData=converter.doConvert(ast, glex)>

<#assign topDecorator = tc.instantiate("de.monticore.sc2cd.SCTopDecorator", [hwPath])>
${topDecorator.decorate(cdData.getCompilationUnit())}

<#-- we override all Attribute templates -->
<#assign hp = tc.instantiate("de.monticore.generating.templateengine.TemplateHookPoint", ["configTemplate.CustomAttribute"])>
${glex.replaceTemplate("cd2java.Attribute", hp)}

<#-- We also add our own method to the -->
${cd4c.addMethod(cdData.getStateSuperClass(), "configTemplate.CustomStateSuperClassGetName")}

${generator.generate(cdData.getCompilationUnit())}
