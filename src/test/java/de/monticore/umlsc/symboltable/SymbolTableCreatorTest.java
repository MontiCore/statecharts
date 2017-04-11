/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.symboltable;

import java.nio.file.Paths;

import org.junit.Test;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._symboltable.StatechartKind;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbol;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @version $Revision$, $Date$
 * @since   TODO: add version number
 *
 */
public class SymbolTableCreatorTest {

	@Test
	public void testFromFile() {
		StatechartWithJavaLanguage language = new StatechartWithJavaLanguage();
		ResolvingConfiguration resolverConf = new ResolvingConfiguration();
		resolverConf.addDefaultFilters(language.getResolvers());
		ModelPath modelPath = new ModelPath(Paths.get("src/test/resources"));
		
		
		
		GlobalScope globalScope = new GlobalScope(modelPath, language, resolverConf);
	    StatechartSymbol sc = globalScope.<StatechartSymbol>resolve("Test1",StatechartKind.KIND).orElse(null);
	    System.out.println(sc);
	}
	
	
	
	@Test
	public void testFromAST() {
		
	}

}
