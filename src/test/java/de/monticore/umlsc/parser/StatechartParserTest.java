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

import de.monticore.umlsc._ast.ASTSCCompilationUnit;
import de.monticore.umlsc._parser.SCCompilationUnitMCParser;
import de.monticore.umlsc._parser.StatechartWithJavaParserFactory;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;

public class StatechartParserTest {
  
  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

  @Test
  public void testSingleState() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/parser/SingleState.sc");
    SCCompilationUnitMCParser parser = StatechartWithJavaParserFactory.createSCCompilationUnitMCParser();
    Optional<ASTSCCompilationUnit> scDef = parser.parse(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
   
  @Test
  public void testEncryptedDocument() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/parser/EncryptedDocument.sc");
    SCCompilationUnitMCParser parser = StatechartWithJavaParserFactory.createSCCompilationUnitMCParser();
    Optional<ASTSCCompilationUnit> scDef = parser.parse(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  @Test
  public void testSimple() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/parser/Simple.sc");
    SCCompilationUnitMCParser parser = StatechartWithJavaParserFactory.createSCCompilationUnitMCParser();
    Optional<ASTSCCompilationUnit> scDef = parser.parse(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  @Test
  public void testVariants() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/parser/Variants.sc");
    SCCompilationUnitMCParser parser = StatechartWithJavaParserFactory.createSCCompilationUnitMCParser();
    Optional<ASTSCCompilationUnit> scDef = parser.parse(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
  @Test
  public void testWebBidding_login() throws RecognitionException, IOException {
    Path model = Paths.get("src/test/resources/de/monticore/umlsc/parser/WebBidding_login.sc");
    SCCompilationUnitMCParser parser = StatechartWithJavaParserFactory.createSCCompilationUnitMCParser();
    Optional<ASTSCCompilationUnit> scDef = parser.parse(model.toString());
    assertFalse(parser.hasErrors());
    assertTrue(scDef.isPresent());
  }
  
}
