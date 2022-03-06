<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class

-->
/* (c) https://github.com/MontiCore/monticore */
${tc.signature("printer", "package")}

${tc.includeArgs("de.monticore.sc2cd.gen.ClassHeader", [printer, package])}

${tc.includeArgs("de.monticore.sc2cd.gen.ClassBody", [printer])}
}