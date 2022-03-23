/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.types.check.ISynthesize;
import de.monticore.types.check.SynthesizeSymTypeFromMCBasicTypes;
import de.monticore.types.check.TypeCheckResult;
import de.monticore.types.mcbasictypes.MCBasicTypesMill;
import de.monticore.types.mcbasictypes._ast.ASTMCQualifiedName;
import de.monticore.types.mcbasictypes._ast.ASTMCReturnType;
import de.monticore.types.mcbasictypes._ast.ASTMCType;
import de.monticore.types.mcbasictypes._visitor.MCBasicTypesTraverser;

public class SynthesizeSymType implements ISynthesize {

  protected MCBasicTypesTraverser traverser;

  protected TypeCheckResult typeCheckResult;

  protected TypeCheckResult getTypeCheckResult() {
    return this.typeCheckResult;
  }

  public SynthesizeSymType() {
    this.init();
  }

  protected void init() {
    traverser = MCBasicTypesMill.traverser();
    typeCheckResult = new TypeCheckResult();

    SynthesizeSymTypeFromMCBasicTypes synFromBasic = new SynthesizeSymTypeFromMCBasicTypes();
    synFromBasic.setTypeCheckResult(typeCheckResult);

    traverser.add4MCBasicTypes(synFromBasic);
    traverser.setMCBasicTypesHandler(synFromBasic);
  }

  protected MCBasicTypesTraverser getTraverser() {
    return traverser;
  }

  @Override
  public TypeCheckResult synthesizeType(ASTMCType type) {
    this.getTypeCheckResult().reset();
    type.accept(this.getTraverser());
    return this.getTypeCheckResult().copy();
  }

  @Override
  public TypeCheckResult synthesizeType(ASTMCReturnType type) {
    this.getTypeCheckResult().reset();
    type.accept(this.getTraverser());
    return this.getTypeCheckResult().copy();
  }

  @Override
  public TypeCheckResult synthesizeType(ASTMCQualifiedName qName) {
    return null;
  }
}
