package de.monticore.symboltable;

import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.triggeredstatecharts.TriggeredStatechartsTool;
import de.monticore.triggeredstatecharts._symboltable.ITriggeredStatechartsArtifactScope;
import de.monticore.umlstatecharts.UMLStatechartsTool;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ImportsTest extends GeneralAbstractTest {

  @Test
  public void testImportsAreAddedToUMLSCArtifactScope(){
    initUMLStatechartsMill();

    UMLStatechartsTool tool = new UMLStatechartsTool();
    ASTSCArtifact ast = tool.parse("src/test/resources/imports/EmptyWithImportStatement.sc");
    IUMLStatechartsArtifactScope artifactScope = tool.createSymbolTable(ast);

    List<ImportStatement> expected = List.of(new ImportStatement("foo.bar", false), new ImportStatement("bar", true));
    List<ImportStatement> actual = artifactScope.getImportsList();
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getStatement(), actual.get(i).getStatement());
      assertEquals(expected.get(i).isStar(), actual.get(i).isStar());
    }
  }

  @Test
  public void testImportsAreAddedToTriggeredSCArtifactScope(){
    initTriggeredStatechartsMill();

    TriggeredStatechartsTool tool = new TriggeredStatechartsTool();
    ASTSCArtifact ast = tool.parse("src/test/resources/imports/EmptyWithImportStatement.sc");
    ITriggeredStatechartsArtifactScope artifactScope = tool.createSymbolTable(ast);

    List<ImportStatement> expected = List.of(new ImportStatement("foo.bar", false), new ImportStatement("bar", true));
    List<ImportStatement> actual = artifactScope.getImportsList();
    assertEquals(expected.size(), actual.size());

    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getStatement(), actual.get(i).getStatement());
      assertEquals(expected.get(i).isStar(), actual.get(i).isStar());
    }
  }
}
