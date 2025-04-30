/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts._symboltable;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.symboltable.ImportStatement;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
import de.se_rwth.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;

public class UMLStatechartsScopesGenitor extends UMLStatechartsScopesGenitorTOP {

  public UMLStatechartsScopesGenitor(){
    super();
  }

  /**
   * overrides the createFromAST method of the top class to set the name and package
   * name of the artifact scope
   */
  @Override
  public IUMLStatechartsArtifactScope createFromAST(ASTSCArtifact rootNode) {
    Log.errorIfNull(rootNode, "0xAE880 Internal Error: No symbol table defined, because empty (null) AST");
    IUMLStatechartsArtifactScope artifactScope = de.monticore.umlstatecharts.UMLStatechartsMill.artifactScope();
    if(rootNode.isPresentPackage()) {
      artifactScope.setPackageName(rootNode.getPackage().getQName());
    }
    if(rootNode.getStatechart().isPresentSCName()) {
      artifactScope.setName(rootNode.getStatechart().getSCName().get());
    }else{
      String fileName = rootNode.getFilePath().getFileName().toString();
      artifactScope.setName(fileName.substring(0, fileName.lastIndexOf('.')));
    }
    List<ImportStatement> imports = new ArrayList<>();
    for (ASTMCImportStatement it : rootNode.getMCImportStatementList()) {
      imports.add(new ImportStatement(it.getQName(), it.isStar()));
    }
    artifactScope.setImportsList(imports);
    putOnStack(artifactScope);
    rootNode.accept(getTraverser());
    return artifactScope;
  }
}
