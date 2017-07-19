/*
 * Copyright (c) 2017 RWTH Aachen. All rights reserved.
 *
 * http://www.se-rwth.de/ 
 */
package de.monticore.umlsc.statechartwithjava._symboltable;

import java.util.Optional;

import de.monticore.ast.ASTNode;
import de.monticore.modelloader.ModelingLanguageModelLoader;
import de.monticore.symboltable.MutableScope;
import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.symboltable.SymbolTableCreator;
import de.monticore.umlsc.statechart._symboltable.SCStateResolvingFilter;
import de.monticore.umlsc.statechart._symboltable.StatechartModelNameCalculator;
import de.monticore.umlsc.statechart._symboltable.StatechartResolvingFilter;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
 * @since   TODO: add version number
 *
 */
public class StatechartWithJavaLanguage extends StatechartWithJavaLanguageTOP {

	/**
	 * Constructor for de.monticore.umlsc.statechartwithjava._symboltable.StatechartWithJavaLanguage.
	 * @param langName
	 * @param fileEnding
	 */
	public StatechartWithJavaLanguage() {
		super("Statechart Language", "sc");

		setModelNameCalculator(new StatechartModelNameCalculator());
	}

	/**
	 * @see de.monticore.ModelingLanguage#getSymbolTableCreator(de.monticore.symboltable.ResolvingConfiguration, de.monticore.symboltable.MutableScope)
	 */
	@Override
	public Optional<? extends SymbolTableCreator> getSymbolTableCreator(ResolvingConfiguration resolvingConfiguration,
			MutableScope enclosingScope) {
    return Optional.of(new StatechartSymbolTableCreator(resolvingConfiguration, enclosingScope));
	}

	/**
	 * @see de.monticore.CommonModelingLanguage#provideModelLoader()
	 */
	@Override
	protected ModelingLanguageModelLoader<? extends ASTNode> provideModelLoader() {
		return new StatechartWithJavaModelLoader(this);
	}
	
  protected void initResolvingFilters() {
    addResolvingFilter(new SCStateResolvingFilter());
    addResolvingFilter(new StatechartResolvingFilter());
  }

}
