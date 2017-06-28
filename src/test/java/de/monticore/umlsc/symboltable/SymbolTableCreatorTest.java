/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.symboltable;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaSymbolTableCreator;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @version $Revision$, $Date$
 * @since   TODO: add version number
 *
 */
public class SymbolTableCreatorTest {

  @BeforeClass
  public static void setup() {
    Slf4jLog.init();
    Log.enableFailQuick(false);
  }

	@Test
	public void testFromFile() throws IOException {
		StatechartWithJavaLanguage language = new StatechartWithJavaLanguage();
		ResolvingConfiguration resolverConf = new ResolvingConfiguration();
		resolverConf.addDefaultFilters(language.getResolvers());
		ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/"));

		StatechartWithJavaParser parser = language.getParser();

		Optional<ASTSCArtifact> opAst = parser.parse("src/test/resources/de/monticore/umlsc/examples/ScSimpleState.sc");

    System.out.println("Hallo");
    for(Finding l : Log.getFindings()) {
      System.out.println(l);
    }



		ASTSCArtifact ast =(ASTSCArtifact) opAst.get();
		
		
		GlobalScope globalScope = new GlobalScope(modelPath, language, resolverConf);
		StatechartWithJavaSymbolTableCreator st = new StatechartWithJavaSymbolTableCreator(resolverConf,globalScope);
		st.createFromAST(ast);
		//	  StatechartSymbol sc = globalScope.<StatechartSymbol>resolve("Test1",StatechartKind.KIND).orElse(null);
//	  System.out.println(sc);

    ast.getStatechart().getSymbol().get();

	}
	
	
	
	@Test
	public void testFromAST() {
		
	}

}
