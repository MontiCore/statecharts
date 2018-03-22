package de.monticore.umlsc.tr.statechartwithjavatr.editor;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TRStatechartWithJavaLanguage extends TRStatechartWithJavaLanguageTOP {

	@Override
	protected List<String> getOutlineElementNames() {
		  List<String> list = new ArrayList<>();
		  
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_List");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_Neg");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_Pat");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_Opt");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_Rep");
      
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCTransition_List");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCTransition_Neg");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCTransition_Pat");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCTransition_Opt");
		  list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCTransition_Rep");
      
		  return list;
	  }

	@Override
	protected Dictionary<String, OutlineComposition<?>> getOutlineCandidates(){
		  Dictionary<String, OutlineComposition<?>> dict = super.getOutlineCandidates();

		  // State Handling
		  dict.get("de.monticore.umlsc.tr.statecharttr._ast.ASTSCState_Pat").setImage("icons/state_obj.gif");

		  return dict;
	}

}
