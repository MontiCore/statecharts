package de.monticore._cocos;

import de.monticore.scbasis._ast.ASTStatechart;
import de.monticore.scbasis._cocos.SCBasisASTStatechartCoCo;
import de.monticore.sccompleteness._ast.ASTSCCompleteness;
import de.monticore.sccompleteness._cocos.SCCompletenessASTSCCompletenessCoCo;
import de.monticore.sccompleteness._cocos.SCCompletenessCoCoChecker;

public class NoIncompleteStatesInCompleteStatechart implements SCCompletenessASTSCCompletenessCoCo, SCBasisASTStatechartCoCo {

  @Override
  public void check(ASTStatechart node) {
    SCCompletenessCoCoChecker completenessCoCoChecker = new SCCompletenessCoCoChecker();

    completenessCoCoChecker.handle(node);
  }



  @Override
  public void check(ASTSCCompleteness node) {

  }
}
