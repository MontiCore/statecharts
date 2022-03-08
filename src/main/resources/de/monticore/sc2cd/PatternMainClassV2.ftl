<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java class

-->
/* (c) https://github.com/MontiCore/monticore */
${tc.signature("printer", "package")}

${tc.includeArgs("de.monticore.sc2cd.gen.ClassHeader", [printer, package])}
{

${cd4c.addMethod(ast, "de.monticore.sc2cd.StateSetStateMethodV2")}
${tc.includeArgs("de.monticore.sc2cd.gen.ClassBody", [printer])}

      ${glex.defineHookPoint(tc,"<ClassBody>?PatternMainClassV2:additionalBody",ast, printer, package)}
 
}
