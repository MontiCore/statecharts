/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import com.google.common.base.Joiner;
import de.monticore.GeneralAbstractTest;
import de.monticore.scbasis._ast.ASTNamedStatechart;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test parses example files (without cocos),
 * and checks against expected values
 */
public class FlatSCFilesParserTest extends GeneralAbstractTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Test
  public void testStatechartFoo() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/foo.sc");
    assertInstanceOf(ASTNamedStatechart.class, ast.getStatechart());
    assertEquals("Foo", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(1, ast.getStatechart().getSCStatechartElementList().size());
    assertEquals("Bla", ((ASTSCState) ast.getStatechart().getSCStatechartElementList().get(0)).getName());

  }

  @Test
  public void testStatechart2() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test2.sc");
    assertInstanceOf(ASTNamedStatechart.class, ast.getStatechart());
    assertEquals("Door2", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementList().size());
  }

  @Test
  public void testStatechart3() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test3.sc");
    assertInstanceOf(ASTNamedStatechart.class, ast.getStatechart());
    assertEquals("Door2", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementList().size());
  }

  @Test
  public void testStatechart4() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test4.sc");
    assertInstanceOf(ASTNamedStatechart.class, ast.getStatechart());
    assertEquals("Door1", ((ASTNamedStatechart) ast.getStatechart()).getName());
  }

  @Test
  public void testStatechart5() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test5.sc");
    assertInstanceOf(ASTNamedStatechart.class, ast.getStatechart());
    assertEquals("Door1", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(1, ast.getMCImportStatementList().size());
    assertEquals("java.util.List", ast.getMCImportStatement(0).getQName());
  }

  protected ASTSCArtifact parse(String file)
      throws IOException {
    List<String> files = Files.readAllLines(new File(file).toPath());
    Optional<ASTSCArtifact> opt = parser.parse_StringSCArtifact(Joiner.on(System.lineSeparator()).join(files));
    Log.getFindings().forEach(System.out::println);
    assertFalse(parser.hasErrors(), "Parsed with errors");
    assertTrue(opt.isPresent(), "No AST present");
    return opt.get();
  }

}
