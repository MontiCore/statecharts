/* (c) https://github.com/MontiCore/monticore */
package de.monticore.prettyprint;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Aim of this test is to verify the pretty printer output.
 * It does so by comparing the PP output of every .sc example file
 */
public class UMLSCPrettyPrinterTest extends GeneralAbstractTest {
  UMLStatechartsParser parser = new UMLStatechartsParser();
  UMLStatechartsFullPrettyPrinter prettyPrinter = new de.monticore.umlstatecharts._prettyprint.UMLStatechartsFullPrettyPrinter(new IndentPrinter());
  @Test
  public void test() {
    testInDir(new File("src/test/resources/examples"));
  }

  protected void test(File file) {
    try (FileReader fw = new FileReader(file)) {
      Optional<ASTSCArtifact> origAstOpt = parser.parse(fw);

      assertTrue(origAstOpt.isPresent(), "No ast parsed from file " + file.getName());
      String prettyOut = prettyPrinter.prettyprint(origAstOpt.get());

      Optional<ASTSCArtifact> prettyAstOpt = parser.parse_String(prettyOut);

      assertTrue(prettyAstOpt.isPresent(), "No ast parsed from pretty: " + prettyOut);
      assertTrue(prettyAstOpt.get().deepEquals(origAstOpt.get()), "ASTs not deep equaling: " + prettyOut + " in " + file.getName());

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
