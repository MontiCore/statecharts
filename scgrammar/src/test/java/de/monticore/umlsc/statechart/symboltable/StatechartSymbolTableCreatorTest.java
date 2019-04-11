/*
 * Copyright (c) 2017, MontiCore. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechart.symboltable;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.GlobalScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.Scope;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbol;
import de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @since   TODO: add version number
 *
 */
public class StatechartSymbolTableCreatorTest {
	
	private Scope globalScope;



	@Before
	public void setup() {
		final StatechartWithJavaLanguage statechartLanguage = new StatechartWithJavaLanguage();
		
		final ResolvingConfiguration resolverConfiguration = new ResolvingConfiguration();
		resolverConfiguration.addDefaultFilters(statechartLanguage.getResolvingFilters());
		
		final ModelPath modelPath = new ModelPath(Paths.get("src/test/resources"));
		
		globalScope = new GlobalScope(modelPath, statechartLanguage, resolverConfiguration);
		
		
	}
	
	
	@Test
	public void testStatechartSymbolTableCreation() {
		final StatechartSymbol scSymbol = globalScope.<StatechartSymbol>resolve("Test1",StatechartSymbol.KIND).orElse(null);
		System.out.println(scSymbol);
	}

	@Test
	public void testStatechartSymbolTableCreationInPackage() {
		final String fullName =  "de.monticore.umlsc.symboltable.Test1";
		final StatechartSymbol scSymbol = globalScope.<StatechartSymbol>resolve(fullName ,StatechartSymbol.KIND).orElse(null);
		assertNotNull(scSymbol);
		assertEquals(fullName, scSymbol.getFullName());
	}
}
