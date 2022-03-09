/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import com.google.common.collect.Lists;
import de.monticore.cd4code.prettyprint.CD4CodeFullPrettyPrinter;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.sc2cd.SC2CDConverter;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SC2CDTest {
  
  @Test
  public void testUMLSC2CD() throws IOException {
    Log.enableFailQuick(false);
    UMLStatechartsMill.init();
    Optional<ASTSCArtifact> opt = UMLStatechartsMill.parser().parse("src/test/resources/examples/uml/DoorExample.sc");
  
    // Build ST
    UMLStatechartsMill.scopesGenitorDelegator().createFromAST(opt.get());
  
    CD4CodeFullPrettyPrinter fullPrettyPrinter = new CD4CodeFullPrettyPrinter();
  
    //    // Prepare CD4C
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    GeneratorSetup config = new GeneratorSetup();
    config.setGlex(glex);
    config.setOutputDirectory(new File("target/gen"));
    config.setTracing(false);
    File templatePath = new File("src/main/resources");
    config.setAdditionalTemplatePaths(Lists.newArrayList(templatePath));
  
    System.out.println(fullPrettyPrinter.prettyprint(new SC2CDConverter().doConvertUML(opt.get(), config).getCompilationUnit()));
    // the content of the generated files is to be checked manually at the moment
  }

  @Test
  public void testTriggeredSC2CD() throws IOException {
    Log.enableFailQuick(false);
    TriggeredStatechartsMill.init();
    Optional<ASTSCArtifact> opt = TriggeredStatechartsMill.parser().parse("src/test/resources/examples/triggered/DoorExample2.sc");

    //Build ST
    TriggeredStatechartsMill.scopesGenitorDelegator().createFromAST(opt.get());

    CD4CodeFullPrettyPrinter fullPrettyPrinter = new CD4CodeFullPrettyPrinter();

    //    // Prepare CD4C
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    GeneratorSetup config = new GeneratorSetup();
    config.setGlex(glex);
    config.setOutputDirectory(new File("target/gen"));
    config.setTracing(false);
    File templatePath = new File("src/main/resources");
    config.setAdditionalTemplatePaths(Lists.newArrayList(templatePath));

    System.out.println(fullPrettyPrinter.prettyprint(new SC2CDConverter().doConvertTriggered(opt.get(), config).getCompilationUnit()));
    // the content of the generated files is to be checked manually at the moment
  }

}
