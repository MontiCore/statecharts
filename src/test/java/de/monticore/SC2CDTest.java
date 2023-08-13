/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import com.google.common.collect.Lists;
import de.monticore.cd.codegen.CDGenerator;
import de.monticore.cd.codegen.CdUtilsPrinter;
import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.prettyprint.CD4CodeFullPrettyPrinter;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.sc2cd.SC2CDConverter;
import de.monticore.sc2cd.SC2CDData;
import de.monticore.sc2cd.SC2CDTriggeredConverter;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class SC2CDTest extends GeneralAbstractTest{

  @Override
  @Before
  public void setUp() {
      super.initLogger();
  }


  @Test
  public void testUMLSC2CD() throws IOException {
    initUMLStatechartsMill();
    Optional<ASTSCArtifact> opt = UMLStatechartsMill.parser().parse("src/test/resources/examples/uml/DoorExample.sc");
    // Build ST
    UMLStatechartsMill.scopesGenitorDelegator().createFromAST(opt.get());
  
    CD4CodeFullPrettyPrinter fullPrettyPrinter = new CD4CodeFullPrettyPrinter();
  
    //    // Prepare CD4C
    GlobalExtensionManagement glex = new GlobalExtensionManagement();
    GeneratorSetup config = new GeneratorSetup();
    config.setGlex(glex);
    config.setOutputDirectory(new File("target/gen-uml-sc"));
    config.setTracing(false);
    glex.setGlobalValue("cdPrinter", new CdUtilsPrinter());
    File templatePath = new File("src/main/resources");
    config.setAdditionalTemplatePaths(Lists.newArrayList(templatePath));
    SC2CDConverter converter = new SC2CDConverter();
    SC2CDData result = converter.doConvert(opt.get(), config.getGlex());
    fullPrettyPrinter.prettyprint(result.getCompilationUnit());
    // the content of the generated files is to be checked manually at the moment



    CDGenerator generator = new CDGenerator(config);
    generator.generate(result.getCompilationUnit());
  }

  @Test
  public void testTriggeredSC2CD() throws IOException {
    initTriggeredStatechartsMill();
    checkTriggeredSC2CD("src/test/resources/examples/triggered/DoorExample2.sc");
  }

  @Test
  public void testTriggeredSC2CDWithInitBlock() throws IOException {
    initTriggeredStatechartsMill();
    checkTriggeredSC2CD("src/test/resources/examples/triggered/LoopSystemOut.sc");
  }

  public void checkTriggeredSC2CD(String modelLocation) throws IOException {
    initTriggeredStatechartsMill();
    Optional<ASTSCArtifact> opt = TriggeredStatechartsMill.parser().parse(modelLocation);

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
    CD4C.init(config);

    SC2CDTriggeredConverter converter = new SC2CDTriggeredConverter();
    SC2CDData result = converter.doConvert(opt.get(), config.getGlex());
    fullPrettyPrinter.prettyprint(result.getCompilationUnit());
    // the content of the generated files is to be checked manually at the moment
  }
}
