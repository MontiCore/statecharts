/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

/**
 * Aim of this test is to verify the pretty printer output.
 * It does so by comparing the PP output of every .sc example file
 */
public class UMLSCPrettyPrinterTest {
  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsPrettyPrinterDelegator prettyPrinterDelegator = new UMLStatechartsPrettyPrinterDelegator();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void test() {
    testInDir(new File("src/test/resources/examples"));
  }

  protected void test(File file) {
    try (FileReader fw = new FileReader(file)) {
      Optional<ASTSCArtifact> origAstOpt = parser.parse(fw);

      Assert.assertTrue("No ast parsed from file " + file.getName(), origAstOpt.isPresent());
      String prettyOut = prettyPrinterDelegator.prettyprint(origAstOpt.get());

      Optional<ASTSCArtifact> prettyAstOpt = parser.parse_String(prettyOut);

      Assert.assertTrue("No ast parsed from pretty: " + prettyOut, prettyAstOpt.isPresent());
      Assert.assertTrue("ASTs not deep equaling: " + prettyAstOpt, prettyAstOpt.get().deepEquals(origAstOpt.get()));

      System.out.println("File " + file.getName() + " passed the PP test");
    }
    catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  protected void testInDir(File dir) {
    for (File f : dir.listFiles()) {
      if (f.isDirectory()) {
        testInDir(f);
      }
      else if (f.getName().endsWith(".sc")) {
        test(f);
      }
    }
  }

}
