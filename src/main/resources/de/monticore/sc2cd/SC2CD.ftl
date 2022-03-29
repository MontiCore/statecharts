<#-- (c) https://github.com/MontiCore/monticore -->
${tc.signature("glex", "converter", "hwPath", "generator")}

<!-- ====================================================================
     build classdiagram
-->
<#assign cdata=converter.doConvert(ast, glex)>

<!-- ====================================================================
     call TopDecorator
-->
<#assign topDecorator = tc.instantiate("de.monticore.sc2cd.SCTopDecorator", [hwPath])>
${topDecorator.decorate(cdata.getCompilationUnit())}

<!-- ====================================================================
     Generate Java-classes
-->
${generator.generate(cdata.getCompilationUnit())}
