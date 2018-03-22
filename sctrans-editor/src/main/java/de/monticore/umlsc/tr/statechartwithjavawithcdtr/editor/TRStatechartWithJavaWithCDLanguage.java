package de.monticore.umlsc.tr.statechartwithjavawithcdtr.editor;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class TRStatechartWithJavaWithCDLanguage extends TRStatechartWithJavaWithCDLanguageTOP {

	@Override
	protected List<String> getOutlineElementNames() {
		  List<String> list = new ArrayList<>();
		
		// CDDefinition
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDDefinition_List");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDDefinition_Neg");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDDefinition_Pat");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDDefinition_Opt");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDDefinition_Rep");
		  
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
		
			list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTStatechart_List");
			list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTStatechart_Neg");
			list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTStatechart_Pat");
			list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTStatechart_Opt");
			list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTStatechart_Rep");
		
		// SCArtifact
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCArtifact_List");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCArtifact_Neg");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCArtifact_Pat");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCArtifact_Opt");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCArtifact_Rep");
		
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCInternTransition_List");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCInternTransition_Neg");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCInternTransition_Pat");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCInternTransition_Opt");
		list.add("de.monticore.umlsc.tr.statecharttr._ast.ASTSCInternTransition_Rep");
		
		// CDDefinition
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDClass_List");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDClass_Neg");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDClass_Pat");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDClass_Opt");
		list.add("de.monticore.umlcd4a.tr.cd4analysistr._ast.ASTCDClass_Rep");
    
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
