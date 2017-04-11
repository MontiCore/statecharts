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
import org.junit.Ignore;
import org.junit.Test;

import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechart._ast.ASTStatechart;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;

public class ExamplesParser {
  
  
  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Test
  @Ignore
  public void testSingleState() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/Simple.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  
  @Test
  @Ignore
  public void testScWithClassReference() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScWithClassReference.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTSCArtifact> scDef = parser.parseSCArtifact(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    assertTrue(scDef.get().getStatechart().classNameIsPresent());
  }
  
  @Test
  public void testScWithOutClassReference() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScWithOutClassReference.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTStatechart> scDef = parser.parseStatechart(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    
  }
  
  @Test
  public void testSimpleState() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScSimpleState.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTStatechart> scDef = parser.parseStatechart(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    
  }
  
  
  @Test
  @Ignore
  public void testScSimpleSTransition() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScSimpleTransition.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTStatechart> scDef = parser.parseStatechart(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    
  }
  
  @Test
  @Ignore
  public void testScTransitionExt() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScTransitionExt.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTStatechart> scDef = parser.parseStatechart(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    
  }
  
  @Test
  public void testScTransitionInternal() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/examples/ScSImpleTransitionInternal.sc");
    StatechartWithJavaParser parser = new StatechartWithJavaParser(); 
    Optional<ASTStatechart> scDef = parser.parseStatechart(model.toString());
    for(Finding f : Log.getFindings()) {
    	System.out.println(f.buildMsg());
    }
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
    
  }
  
}
