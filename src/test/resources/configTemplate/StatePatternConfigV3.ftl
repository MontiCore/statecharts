<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  This template controller configures StatePatternV3

  Call it using the CLI: .. -fp ssrc/main/resources -ct de/monticore/sc2cd/StatePatternConfigV3.ftl

  This template is then executed after the SC2CD mapping has been perfomed, 
  but before the final printing is executed.
  This is a perfect place to:
  1. add additional classes to the ASTCDCompilationUnit
  2. add additional mthods and attributes to each of the already existing classes
  3. fill out some of the various HookPoints in the used templates
  4. replace templates by local variants 
  
  This template receives the following parameters:
    glex, tc:   -- as usual

    ASTSCArtifact ast:              the AST artifact
    SC2CDConverterUMLV2 converter:  the SD to CD converter
    MCPath hwPath:                  the path for the handwritten code
    CDGenerator generator:          the generator that generates source code from class diagrams
-->
${tc.signature("glex", "converter", "hwPath", "generator")}

<!-- ====================================================================
     Additional configuration of the StatechartClass -->


<!-- ====================================================================
     these HookPoints are specific to the StatechartClass 
     * we have added add attribute "demoVariableI21" to the StatechartClass
       and use it in the constructor 
     * we add a local variable "demoVariableI11" to the setState-Method
-->

${glex.bindStringHookPoint("<Statement>*StateInitConstructorV2:begin", 
                           "demoVariableI21 = 0; /* ** myOwn Hookpoint2 ** */")}
${glex.bindStringHookPoint("<Statement>*StateInitConstructorV2:end", 
                           "demoVariableI21++; /* ** myOwn Hookpoint3 ** */")}

${glex.bindStringHookPoint("<Statement>*StateSetStateMethodV2:end",
                           "count++;
                            int demoVariableI11 = 0; /* ** myOwn Hookpoint1 ** */")}

<!-- ====================================================================
     this HookPoint is added to each state 

// TODO it makes no sense to add it in all classes (: to be more specific)

-->

${glex.bindTemplateHookPoint("ClassContent:Elements", "configTemplate/MonitoringV3.ftl")}

<!-- ====================================================================
     build classdiagram
-->
<#assign cdata=converter.doConvert(ast, glex)>

<!-- ====================================================================
     call TopDecorator
-->
<#assign topDecorator = tc.instantiate("de.monticore.cd.codegen.TopDecorator", [hwPath])>
${topDecorator.decorate(cdata.getCompilationUnit())}

<!-- ====================================================================
     Generate Java-classes
-->
${generator.generate(cdata.getCompilationUnit())}


