package de.monticore.sc2cd;

import com.google.common.collect.Lists;
import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cd4code.prettyprint.CD4CodeFullPrettyPrinter;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;
import de.monticore.generating.GeneratorSetup;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import de.se_rwth.commons.logging.Log;

import java.io.File;
import java.util.Optional;

public class SC2CDConverter {
  /**
   * For testing
   * TODO: REMOVE ME
   * @param args
   * @throws Exception
   */
  public static void main(String[] args) throws Exception {
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
    config.setOutputDirectory(new File("target/generated-sources/mc"));
    config.setOutputDirectory(new File("target/gen"));
    config.setTracing(false);
    File templatePath = new File("src/main/resources");
    config.setAdditionalTemplatePaths(Lists.newArrayList(templatePath));

    System.out.println(fullPrettyPrinter.prettyprint(new SC2CDConverter().doConvert(opt.get(), config)));
  }

  /**
   * Convert a SC to a CD
   * @param astscArtifact the SC
   * @return the CD
   */
  public ASTCDCompilationUnit doConvert(ASTSCArtifact astscArtifact, GeneratorSetup config) {
    CD4C cd4C = new CD4C(config);
    cd4C.setEmptyBodyTemplate("de.monticore.sc2cd.gen.EmptyMethod");

    // Phase 1: Work on states
    SC2CDStateVisitor phase1Visitor = new SC2CDStateVisitor(cd4C);
    UMLStatechartsTraverser traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase1Visitor);
    traverser.add4UMLStatecharts(phase1Visitor);

    CD4CodeMill.init();
    traverser.handle(astscArtifact);

    // Phase 2: Work with transitions
    SC2CDTransitionVisitor phase2Visitor = new SC2CDTransitionVisitor(phase1Visitor.getScClass(),
                                                                      phase1Visitor.getStateToClassMap(),
                                                                      phase1Visitor.getStateSuperClass(),
                                                                      cd4C);
    traverser = UMLStatechartsMill.inheritanceTraverser();
    traverser.add4SCBasis(phase2Visitor);
    traverser.add4UMLStatecharts(phase2Visitor);
    traverser.add4SCTransitions4Code(phase2Visitor);
    traverser.handle(astscArtifact);

    // voila
    return phase1Visitor.getCdCompilationUnit();
  }
}
