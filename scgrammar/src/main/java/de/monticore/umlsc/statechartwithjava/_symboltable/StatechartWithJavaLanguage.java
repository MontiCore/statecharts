/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import java.util.Optional;

import de.monticore.ast.ASTNode;
//import de.monticore.modelloader.ModelingLanguageModelLoader;
//import de.monticore.symboltable.ResolvingConfiguration;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbolTableCreator;
import de.monticore.umlsc.statechart._symboltable.IStatechartScope;
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
	 */
	public StatechartWithJavaLanguage() {
		super("Statechart Language", "sc");


	}

	/**
	 *  de.monticore.IModelingLanguage#getSymbolTableCreator(de.monticore.symboltable.IScope)
	 */
	public Optional<? extends StatechartSymbolTableCreator> getSymbolTableCreator(IStatechartScope enclosingScope) {
    return Optional.of(new StatechartSymbolTableCreator(enclosingScope));
	}

	/**
	 *  de.monticore.CommonModelingLanguage#provideModelLoader()
	 */
	@Override
	protected StatechartWithJavaModelLoader provideModelLoader() {
		return new StatechartWithJavaModelLoader(this);
	}

}
