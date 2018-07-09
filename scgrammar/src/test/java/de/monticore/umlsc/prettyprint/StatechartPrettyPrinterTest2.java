package de.monticore.umlsc.prettyprint;

import de.monticore.umlsc.statechart._ast.*;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Log;

import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StatechartPrettyPrinterTest2 {

  @BeforeClass
  public static void init() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Before
  public void setUp() {
    Log.getFindings().clear();
  }


  @Test
  public void testASTInvariant() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTInvariant> ast = parser.parseString_Invariant("[ n+b ]");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTInvariant invariant = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_Invariant(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(invariant.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCMethodCall() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCMethodCall> ast = parser.parseString_SCMethodCall("name.some.other ( a , b )");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCMethodCall scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCMethodCall(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCReturnStatement() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCReturnStatement> ast = parser.parseString_SCReturnStatement("return ( a+ b.sid)");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCReturnStatement scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCReturnStatement(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCCode() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCCode> ast = parser.parseString_SCCode("code { block; }");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCCode scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCCode(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArguments() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArguments> ast = parser.parseString_SCArguments("( ab, b+d, abkd.skid )");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArguments scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCArguments(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCInternTransition() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCInternTransition> ast = parser.parseString_SCInternTransition("<< name = \"stereo\" >> -> / { block.state; } ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCInternTransition scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCInternTransition(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCTransitionBody() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCTransitionBody> ast = parser.parseString_SCTransitionBody("[ inv ] / { block.statement; } [ endiv ] ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCTransitionBody scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCTransitionBody(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCTransition() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCTransition> ast = parser.parseString_SCTransition("<< name = \"somestring\"  >>  Start -> End [ inv ] /");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCTransition scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCTransition(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCStereotype() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCStereotype> ast = parser.parseString_SCStereotype("<< name = \"somestring\" , name2 = \"other\" >>");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCStereotype scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCStereotype(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCStereoValue() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCStereoValue> ast = parser.parseString_SCStereoValue("name = \"abcd\"");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCStereoValue scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCStereoValue(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCAction() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCAction> ast = parser.parseString_SCAction("[ pre ] / { block; } [ post ]");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCAction scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCAction(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCModifier() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCModifier> ast = parser.parseString_SCModifier("<< name = \"stereo\" >> final");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCModifier scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_SCModifier(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTCompleteness() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTCompleteness> ast = parser.parseString_Completeness(" (...) ");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTCompleteness scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseString_Completeness(output);
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }


  @Test
  public void testASTSCArtifact1() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/Banking.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }


  @Test
  public void testASTSCArtifact2() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleState.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact3() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransition.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact4() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransitionInternal.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact5() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/ScTransitionExt.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

  @Test
  public void testASTSCArtifact6() throws IOException{
    StatechartWithJavaParser parser = new StatechartWithJavaParser();
    Optional<ASTSCArtifact> ast = parser.parseSCArtifact("src/test/resources/de/monticore/umlsc/examples/Simple.sc");
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    ASTSCArtifact scMethodCall = ast.get();
    StatechartPrettyPrinter pp = new StatechartPrettyPrinter();
    String output = pp.prettyPrint(ast.get());
    ast = parser.parseSCArtifact(new StringReader(output));
    assertTrue(ast.isPresent());
    assertFalse(parser.hasErrors());
    assertTrue(scMethodCall.deepEquals(ast.get()));
  }

}
