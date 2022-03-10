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
    TODO: was ist mit der Variable ast?  ich vermute ASTCDCompilationUnit
    
    HookPointService hpService:   a helper -- TODO #3100 to remove (ersatz: glex)

    ASTCDClass scClass:           the class representing the state chart
    ASTCDClass stateSuperClass:   abstract super class for the states
    Collection<ASTCDClass> stateClasses:  collection of all states
    
    TODO: es fehlen: ASTCDCompilationUnit  und auch  das QuellModell: SCArtifact
    am besten ist es wohl, dies alles in SC2CD Klasse zu verpacken und 
    das Objekt als Ganzes zu Übergeben --> spart parameter
-->
${tc.signature("glex", "hpService", "scClass", "stateSuperClass", "stateClasses")}

<!-- ====================================================================
     Additional configuration of the StatechartClass -->


<!-- ====================================================================
     these HookPoints are specific to the StatechartClass 
     * we have added add attribute "demoVariableI21" to the StatechartClass
       and use it in the constructor 
     * we add a local variable "demoVariableI11" to the setState-Method
-->

    // HP is defined in de/monticore/sc2cd/StateInitConstructorV2.ftl

// TODO Dringend, Prio: #3113
// Ergebnis in target/gentest1MitV3/uml zeigt, dass dieser Hookpoint zwar existiert
// aber nicht gemäß dem angegebenen Wert gebunden ist, sondern unbesetzt bleibt
// .
// warum das so ist erschließt sich mir nicht.
// meine einzige Vermutung könnte sein, dass  cd4c  addConstruktor anders behandelt als addMethod
// und daher die Templates für die Konstruktoren früher auswertet, noch bevor das Programm 
// an diese Stelle kommt.
// end TODO
${glex.bindStringHookPoint("<Statement>*StateInitConstructorV2:begin", 
                           "demoVariableI21 = 0; /* ** myOwn Hookpoint2 ** */")}
${glex.bindStringHookPoint("<Statement>*StateInitConstructorV2:end", 
                           "demoVariableI21++; /* ** myOwn Hookpoint3 ** */")}


    // HP is defined in de/monticore/sc2cd/StateSetStateMethodV2.ftl
    // count is added in de/monticore/sc2cd/MonitoringV3.ftl (see below)
            // BR@MB #3113: hier klappt das korrekt wie in target/gentest1MitV3/uml zu besichtigen  (TODO: delete comment)
${glex.bindStringHookPoint("<Statement>*StateSetStateMethodV2:end", 
                           "count++;
                            int demoVariableI11 = 0; /* ** myOwn Hookpoint1 ** */")}


<!-- ====================================================================
     this HookPoint is added to all classes 
        
// TODO it makes no sense to add it in all classes (: to be more specific)
-->

    // HP is defined in de/monticore/sc2cd/gen/ClassBody.ftl (used for all classes)
            // BR@MB #3113: hier klappt das korrekt wie in target/gentest1MitV3/uml zu besichtigen  (TODO: delete comment)
$ {glex.bindStringHookPoint("<ClassBodyDecl>*gen.ClassBody:additionalBody", 
                           "int demoVariableI21; /* ** myOwn Hookpoint4 ** */")}

<!-- ====================================================================
     this HookPoint is added to each state 

// TODO it makes no sense to add it in all classes (: to be more specific)

-->

${glex.bindTemplateHookPoint("<ClassBodyDecl>*gen.ClassBody:additionalBody", 
                           "de/monticore/sc2cd/MonitoringV3.ftl")}

