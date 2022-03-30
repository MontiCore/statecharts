/* (c) https://github.com/MontiCore/monticore */
package de.monticore.sc2cd;

import de.monticore.cd.methodtemplates.CD4C;
import de.monticore.cd4code.CD4CodeMill;
import de.monticore.cdbasis.CDBasisMill;
import de.monticore.cdbasis._ast.ASTCDClass;
import de.monticore.cdbasis._ast.ASTCDCompilationUnit;
import de.monticore.cdbasis._ast.ASTCDCompilationUnitBuilder;
import de.monticore.cdbasis._ast.ASTCDDefinition;
import de.monticore.generating.templateengine.GlobalExtensionManagement;
import de.monticore.scbasis._ast.ASTNamedStatechart;
import de.monticore.scbasis._ast.ASTSCArtifact;
import de.monticore.scbasis._ast.ASTSCState;
import de.monticore.scbasis._visitor.SCBasisVisitor2;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedType;
import de.monticore.umlmodifier.UMLModifierMill;
import de.se_rwth.commons.Splitters;
import de.se_rwth.commons.logging.Log;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Phase 1: Create the State Pattern classes based on the states
 */
public class SC2CDStateVisitorV1
        implements SCBasisVisitor2 {

  public final static String ERROR_CODE = "0xDC011";

  protected ASTSCArtifact astscArtifact;

  protected ASTCDCompilationUnit cdCompilationUnit;

  /**
   * The StateCharts class
   */
  protected ASTCDClass scClass;
  /**
   * The super class for all state implementations
   */
  protected ASTCDClass stateSuperClass;
  /**
   * Mapping of the state implementation classes for every state
   */
  protected final Map<String, ASTCDClass> stateToClassMap = new HashMap<>();
  /**
   * Code template reference
   */
  protected final CD4C cd4C;

  protected final GlobalExtensionManagement glex;

  /**
   * Name of the initial state
   */
  protected String initialState = "";

  public SC2CDStateVisitorV1(GlobalExtensionManagement glex) {
    this.cd4C = CD4C.getInstance();
    this.glex = glex;
  }


  @Override
  public void visit(ASTSCArtifact scArtifact) {
    astscArtifact = scArtifact;
  }

  @Override
  public void endVisit(ASTSCArtifact scArtifact) {
    // Generate the constructor of the class
    // It inits all state attributes
    // and the initial state
    this.cd4C.addConstructor(this.scClass, "de.monticore.sc2cd.StateInitConstructorV1",
                             scClass.getName(),
                             this.stateToClassMap.keySet(),
                             this.initialState);
  }

  @Override
  public void visit(ASTNamedStatechart statechart) {
    // Add a CDDefinition for every statechart
    ASTCDDefinition astcdDefinition = CDBasisMill.cDDefinitionBuilder().setName(statechart.getName())
            .setModifier(UMLModifierMill.modifierBuilder().build()).build();

    ASTCDCompilationUnitBuilder cdCompilationUnitBuilder = CDBasisMill.cDCompilationUnitBuilder();
    if (this.astscArtifact.isPresentPackage()) {
      cdCompilationUnitBuilder.setMCPackageDeclaration(
              CDBasisMill.mCPackageDeclarationBuilder().setMCQualifiedName(this.astscArtifact.getPackage()).build());
    }
    cdCompilationUnitBuilder.setMCImportStatementsList(this.astscArtifact.getMCImportStatementList());
    cdCompilationUnitBuilder.setCDDefinition(astcdDefinition);

    cdCompilationUnit = cdCompilationUnitBuilder.build();

    // Main class, names equally to the SC
    scClass = CDBasisMill.cDClassBuilder().setName(statechart.getName())
            .setModifier(CDBasisMill.modifierBuilder().setPublic(true).build()).build();
    astcdDefinition.addCDElement(scClass);
    // replace the template to add a setState method
    this.cd4C.addMethod(this.scClass, "de.monticore.sc2cd.StateSetStateMethodV1");

  }
  
  /**
   *  used endVisit as CD4C currently requires classes to have scopes already 
   *  */
  @Override
  public void endVisit(ASTNamedStatechart statechart) {
    // The super class for states, has a handle{Stimulus} method
    stateSuperClass = CDBasisMill.cDClassBuilder().setName("StateClass")
            .setModifier(CDBasisMill.modifierBuilder().setAbstract(true).build()).build();
    cdCompilationUnit.getCDDefinition().addCDElement(stateSuperClass);

    CD4CodeMill.scopesGenitorDelegator().createFromAST(cdCompilationUnit);

    // The "current state" attribute on the class
    cd4C.addAttribute(scClass, "protected StateClass state;");
  }

  // TODO: public void visit(ASTUnnamedStatechart statechart)


  @Override
  public void visit(ASTSCState state) {
    if (state.getName().equals("state")) {
      Log.error(ERROR_CODE + "State is named \"state\", which interferes with the attribute for the currently selected state");
    }
    if (initialState.isEmpty()) {
      initialState = state.getName();
    }

    // A class extending StateClass for this state
    ASTCDClass stateClass = CDBasisMill.cDClassBuilder().setName(state.getName())
            .setModifier(CDBasisMill.modifierBuilder().build())
            .setCDExtendUsage(CDBasisMill.cDExtendUsageBuilder().addSuperclass(qualifiedType("StateClass")).build())
            .build();
    // Add the StateClassImpl to the CD and mapping
    this.cdCompilationUnit.getCDDefinition().addCDElement(stateClass);
    this.stateToClassMap.put(state.getName(), stateClass);

    // Add reference to this in the main class, in form of an attribute
    cd4C.addAttribute(scClass, "protected " + state.getName() + " " + StringUtils.uncapitalize(state.getName()) + ";");

    // Set the initial state
    if (state.getSCModifier().isInitial()) {
      this.initialState = state.getName();
    }
  }

  public ASTCDCompilationUnit getCdCompilationUnit() {
    return cdCompilationUnit;
  }

  public ASTCDClass getScClass() {
    return scClass;
  }

  public Map<String, ASTCDClass> getStateToClassMap() {
    return stateToClassMap;
  }

  public ASTCDClass getStateSuperClass() {
    return stateSuperClass;
  }

  // Support methods
  protected ASTMCQualifiedType qualifiedType(String qname) {
    return qualifiedType(Splitters.DOT.splitToList(qname));
  }

  protected ASTMCQualifiedType qualifiedType(List<String> partsList) {
    return CD4CodeMill.mCQualifiedTypeBuilder()
            .setMCQualifiedName(CD4CodeMill.mCQualifiedNameBuilder().setPartsList(partsList).build()).build();
  }
}
