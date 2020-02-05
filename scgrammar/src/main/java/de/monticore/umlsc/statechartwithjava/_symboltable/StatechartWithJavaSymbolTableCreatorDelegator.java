/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlsc.statechartwithjava._symboltable;

import de.monticore.symboltable.ImportStatement;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
import de.monticore.umlsc.statechart._ast.ASTSCArtifact;
import de.monticore.umlsc.statechartwithjava._visitor.StatechartWithJavaDelegatorVisitor;
import de.monticore.utils.Names;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StatechartWithJavaSymbolTableCreatorDelegator extends StatechartWithJavaSymbolTableCreatorDelegatorTOP {

  public StatechartWithJavaSymbolTableCreatorDelegator(IStatechartWithJavaGlobalScope globalScope) {
    super(globalScope);
  }

  public StatechartWithJavaArtifactScope createFromAST(de.monticore.umlsc.statechart._ast.ASTSCArtifact rootNode) {
    StatechartWithJavaArtifactScope as =  statechartWithJavaSTC.createFromAST(rootNode);
    as.setImports(getImportStatements(rootNode));
    as.setPackageName(Names.constructQualifiedName(rootNode.getPackageList()));
    if (as.getName().isPresent()){
      if (!as.getPackageName().isEmpty()){
        globalScope.cache(as.getPackageName() + "." + as.getName().get());
      } else {
        globalScope.cache(as.getName().get());
      }
    }
    return as;
  }

  private List<ImportStatement> getImportStatements(ASTSCArtifact node) {
    List<ImportStatement> imports = new ArrayList<>();
    if (node.getMCImportStatementList() != null) {
      for (ASTMCImportStatement imp : node.getMCImportStatementList()) {
        String qualifiedImport = imp.getQName();
        imports.add(new ImportStatement(qualifiedImport, imp.isStar()));
      }
    }
    return imports;
  }

}
