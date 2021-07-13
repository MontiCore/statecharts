<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class

-->
${tc.signature("printer", "package")}

${tc.includeArgs("de.monticore.sc2cd.gen.ClassHeader", [printer, package])}

${cd4c.addMethod(ast, "de.monticore.sc2cd.StateSetStateMethod")}
${tc.includeArgs("de.monticore.sc2cd.gen.ClassBody", [printer])}
}