<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class

//TODO: diese Datei existiert doppelt in zwei verschiedenen Varianten, eine davon ist zu entfernen oder umzubenennen #3099

-->
/* (c) https://github.com/MontiCore/monticore */
${tc.signature("printer", "package")}

${tc.includeArgs("de.monticore.sc2cd.gen.ClassHeader", [printer, package])}

${cd4c.addMethod(ast, "de.monticore.sc2cd.StateSetStateMethod")}
${tc.includeArgs("de.monticore.sc2cd.gen.ClassBody", [printer])}
}
