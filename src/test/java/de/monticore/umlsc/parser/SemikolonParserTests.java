/*
 * Copyright (c) 2014 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.BeforeClass;
import org.junit.Test;

import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;

public class SemikolonParserTests {
  
  
  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Test
  public void testSingleState() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/semikolontests/ScSimpleState.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    System.out.println(Log.getFindings());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  @Test
  public void testSingleStateEmptyBody() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/semikolontests/ScSimpleStateEmptyBody.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    System.out.println(Log.getFindings());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  @Test
  public void testSingleTransition() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/semikolontests/ScSimpleTransition.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    System.out.println(Log.getFindings());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  
  @Test
  public void testAllTransitions() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/semikolontests/ScAllTransitions.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    System.out.println(Log.getFindings());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
}
