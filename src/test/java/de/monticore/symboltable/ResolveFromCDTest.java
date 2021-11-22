/* (c) https://github.com/MontiCore/monticore */
package de.monticore.symboltable;

import de.monticore.io.paths.MCPath;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._symboltable.SCStateSymbol;
import de.monticore.symbols.basicsymbols.BasicSymbolsMill;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbol;
import de.monticore.symbols.basicsymbols._symboltable.TypeSymbolDeSer;
import de.monticore.symbols.oosymbols._symboltable.FieldSymbol;
import de.monticore.umlstatecharts.UMLStatechartsCLI;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsArtifactScope;
import de.monticore.umlstatecharts._symboltable.IUMLStatechartsGlobalScope;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class ResolveFromCDTest {

  IUMLStatechartsGlobalScope gs;
  
  @BeforeClass
  public static void beforeClass() throws Exception {
    LogStub.init();
  }
  
  @Before
  public void setUp() throws Exception {
    // init mill and global scope
    UMLStatechartsMill.init();
    gs = UMLStatechartsMill.globalScope();
    gs.clear();
    BasicSymbolsMill.initializePrimitives();
  }

  @Test
  public void testResolveFromCD(){

    // set type symbol deser
    gs.putSymbolDeSer("de.monticore.cdbasis._symboltable.CDTypeSymbol", new TypeSymbolDeSer());

    // set symbol path
    MCPath symbolPath = new MCPath("src/test/resources/symtab/fromCD");
    UMLStatechartsMill.globalScope().setSymbolPath(symbolPath);

    // manually load symbol file
    gs.loadFileForModelName("MyTypes");

    // test resolving of types and fields
    Optional<TypeSymbol> type = gs.resolveType("my.Test");
    assertTrue(type.isPresent());

    Optional<FieldSymbol> field = gs.resolveField("my.Test.foo");
    assertTrue(field.isPresent());
  }
}
