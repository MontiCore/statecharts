/* (c) https://github.com/MontiCore/monticore */
package de.monticore.parser;

import com.google.common.base.Joiner;
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
    ASTSCArtifact ast = parse("src/test/resources/examples/flat/foo.sc");
    assertEquals("Foo", ast.getStatechart().getName());
    assertEquals(1, ast.getStatechart().getSCStatechartElementsList().size());
    assertEquals("Bla", ((ASTSCState) ast.getStatechart().getSCStatechartElementsList().get(0)).getName());

  }

  @Test
  public void testStatechart2() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/examples/flat/test2.sc");
    assertEquals("Door2", ast.getStatechart().getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementsList().size());
  }

  @Test
  public void testStatechart3() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/examples/flat/test3.sc");
    assertEquals("Door2", ast.getStatechart().getName());
    assertEquals(3, ast.getStatechart().getSCStatechartElementsList().size());
  }

  @Test
  public void testStatechart4() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/examples/flat/test4.sc");
    assertEquals("Door1", ast.getStatechart().getName());
  }

  @Test
  public void testStatechart5() throws IOException {
    ASTSCArtifact ast = parse("src/test/resources/examples/flat/test5.sc");
    assertEquals("Door1", ast.getStatechart().getName());
    assertEquals(1, ast.getMCImportStatementsList().size());
    assertEquals("java.util.List", ast.getMCImportStatements(0).getQName());
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
