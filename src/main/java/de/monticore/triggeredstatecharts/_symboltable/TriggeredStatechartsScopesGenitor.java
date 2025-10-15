package de.monticore.triggeredstatecharts._symboltable;

import com.google.common.base.Preconditions;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.symboltable.ImportStatement;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.types.mcbasictypes._ast.ASTMCImportStatement;
import de.se_rwth.commons.logging.Log;

import java.util.ArrayList;
import java.util.List;


public class TriggeredStatechartsScopesGenitor extends TriggeredStatechartsScopesGenitorTOP {
    public ITriggeredStatechartsArtifactScope createFromAST(ASTSCArtifact rootNode){
      Preconditions.checkNotNull(rootNode, "0xAE880 Internal Error: No symbol table defined, because empty (null) AST");
      ITriggeredStatechartsArtifactScope artifactScope = TriggeredStatechartsMill.artifactScope();
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