/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.symboltable;

import de.monticore.io.paths.ModelPath;
import de.monticore.symboltable.*;
//import de.monticore.symboltable.mocks.languages.statechart.StateKind;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
//import de.monticore.umlsc.statechart._symboltable.SCStateKind;
import de.monticore.umlsc.statechart._symboltable.SCStateSymbol;
//import de.monticore.umlsc.statechart._symboltable.StatechartKind;
import de.monticore.umlsc.statechart._symboltable.StatechartSymbol;
import de.monticore.umlsc.statechart.prettyprint.StatechartPrettyPrinter;
import de.monticore.umlsc.statechartwithjava._parser.StatechartWithJavaParser;
import de.monticore.umlsc.statechartwithjava._symboltable.*;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.Slf4jLog;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

/**
 * TODO: Write me!
 *
 * @author  (last commit) $Author$
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
		//ResolvingConfiguration resolverConf = new ResolvingConfiguration();
		//resolverConf.addDefaultFilters(language.getResolvingFilters());
		ModelPath modelPath = new ModelPath(Paths.get("src/test/resources/"));

		StatechartWithJavaParser parser = language.getParser();

		Optional<ASTSCArtifact> opAst = parser.parse("src/test/resources/de/monticore/umlsc/examples/Banking.sc");

    System.out.println(opAst.get().getStatechart());
    for(Finding l : Log.getFindings()) {
      System.out.println(l);
    }



		ASTSCArtifact ast =(ASTSCArtifact) opAst.get();
    	System.out.println(ast);
		System.out.println("Hallo2");

		StatechartWithJavaGlobalScope globalScope = new StatechartWithJavaGlobalScope(modelPath, language);
		StatechartWithJavaSymbolTableCreatorDelegator st = new StatechartWithJavaSymbolTableCreatorDelegator(globalScope);
		StatechartWithJavaArtifactScope symTab = st.createFromAST(ast);
		//	  StatechartSymbol sc = globalScope.<StatechartSymbol>resolve("Test1",StatechartKind.KIND).orElse(null);
//	  System.out.println(sc);

		Optional<? extends ISymbol> sym = ast.getStatechart().getSCStateList().get(0).getSymbolOpt();
		System.out.println(ast.getStatechart().getSCStateList().get(0));
		System.out.println(ast.getStatechart().getSCStateList().get(2).getName());
		System.out.println(ast.getStatechart().getSCStateList().get(2).getSymbolOpt());
		assertTrue(sym.isPresent());
		System.out.println(sym.get().getName());
		//String o =new StatechartPrettyPrinter().prettyPrint(ast);
		//System.out.println(o);



		Optional<StatechartSymbol> scResolved = symTab.resolveStatechart("Banking");
		assertTrue(scResolved.isPresent());

		System.out.println(symTab.getSymbolsSize());

		Optional<StatechartSymbol> stateResolv = symTab.resolveStatechart("Offer");
		//assertTrue(stateResolv.isPresent()); //TODO: State loading fails

	}
	
	
	
	@Test
	public void testFromAST() {
		
	}

}
