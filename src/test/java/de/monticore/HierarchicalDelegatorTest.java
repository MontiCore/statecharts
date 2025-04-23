/* (c) https://github.com/MontiCore/monticore */
package de.monticore;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scstatehierarchy.HierarchicalStateCollector;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.monticore.umlstatecharts._visitor.UMLStatechartsTraverser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Aim of this test is to verify the default delegator
 * visitor behaviour on various reporting utilities.
 * We use a statechart with hierarchical states.
 */
public class HierarchicalDelegatorTest extends GeneralAbstractTest {
  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testHierarchicalStateCollector() throws Exception {
    File file = new File("src/test/resources/examples/uml/Car.sc");
    try (FileReader fw = new FileReader(file)) {
      Optional<ASTSCArtifact> origAstOpt = parser.parse(fw);

      assertTrue(origAstOpt.isPresent(), "No ast parsed from file " + file.getName());

      HierarchicalStateCollector stateCollectorVisitor = new HierarchicalStateCollector();

      UMLStatechartsTraverser traverser = UMLStatechartsMill.traverser();
      traverser.add4SCBasis(stateCollectorVisitor);
      traverser.add4SCStateHierarchy(stateCollectorVisitor);

      origAstOpt.get().accept(traverser);

      assertEquals(4, stateCollectorVisitor.getStates().size());
    }
  }

}
