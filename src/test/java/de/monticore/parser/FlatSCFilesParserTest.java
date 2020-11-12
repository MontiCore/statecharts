/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import com.google.common.base.Joiner;
import de.monticore.scbasis._ast.ASTNamedStatechart;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.umlstatecharts._parser.UMLStatechartsParser;
import de.se_rwth.commons.logging.Log;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * This test parses example files (without cocos),
 * and checks against expected values
 */
public class FlatSCFilesParserTest {

  UMLStatechartsParser parser = new UMLStatechartsParser();

  @Before
  public void init() {
    Log.enableFailQuick(false);
  }

  @Test
  public void testStatechartFoo() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/foo.sc");
    assertTrue(ast.getStatechart() instanceof ASTNamedStatechart);
    assertEquals("Foo", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(1, ast.getStatechart().getSCStatechartElementList().size());
    assertEquals("Bla", ((ASTSCState) ast.getStatechart().getSCStatechartElementList().get(0)).getName());

  }

  @Test
  public void testStatechart2() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test2.sc");
    assertTrue(ast.getStatechart() instanceof ASTNamedStatechart);
    assertEquals("Door2", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementList().size());
  }

  @Test
  public void testStatechart3() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test3.sc");
    assertTrue(ast.getStatechart() instanceof ASTNamedStatechart);
    assertEquals("Door2", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementList().size());
  }

  @Test
  public void testStatechart4() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test4.sc");
    assertTrue(ast.getStatechart() instanceof ASTNamedStatechart);
    assertEquals("Door1", ((ASTNamedStatechart) ast.getStatechart()).getName());
  }

  @Test
  public void testStatechart5() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/flat/test5.sc");
    assertTrue(ast.getStatechart() instanceof ASTNamedStatechart);
    assertEquals("Door1", ((ASTNamedStatechart) ast.getStatechart()).getName());
    assertEquals(1, ast.getMCImportStatementList().size());
    assertEquals("java.util.List", ast.getMCImportStatement(0).getQName());
  }

  protected ASTSCArtifact parse(String file)
      throws IOException {
    List<String> files = Files.readAllLines(new File(file).toPath());
    Optional<ASTSCArtifact> opt = parser.parse_StringSCArtifact(Joiner.on(System.lineSeparator()).join(files));
    Log.getFindings().forEach(System.out::println);
    assertFalse("Parsed with errors", parser.hasErrors());
    assertTrue("No AST present", opt.isPresent());
    return opt.get();
  }

}
