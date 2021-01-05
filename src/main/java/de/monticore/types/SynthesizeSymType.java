/* (c) https://github.com/MontiCore/monticore */
package de.monticore.types;

import de.monticore.types.check.ISynthesize;
import de.monticore.types.check.SymTypeExpression;
import de.monticore.types.check.SynthesizeSymTypeFromMCBasicTypes;
import de.monticore.types.check.TypeCheckResult;
import de.monticore.types.mcbasictypes.MCBasicTypesMill;
import de.monticore.types.mcbasictypes._visitor.MCBasicTypesTraverser;

import java.util.Optional;

public class SynthesizeSymType implements ISynthesize {
  
  protected MCBasicTypesTraverser traverser;
  
  protected TypeCheckResult typeCheckResult;
  
  @Override public Optional<SymTypeExpression> getResult() {
    if(typeCheckResult.isPresentCurrentResult()){
      return Optional.of(typeCheckResult.getCurrentResult());
    }else{
      return Optional.empty();
    }
  }
  
  @Override public void init() {
    traverser = MCBasicTypesMill.traverser();
    typeCheckResult = new TypeCheckResult();
  
    SynthesizeSymTypeFromMCBasicTypes synFromBasic = new SynthesizeSymTypeFromMCBasicTypes();
    synFromBasic.setTypeCheckResult(typeCheckResult);
  
    traverser.add4MCBasicTypes(synFromBasic);
    traverser.setMCBasicTypesHandler(synFromBasic);
    
  }
  
  @Override public MCBasicTypesTraverser getTraverser() {
    return traverser;
  }
}
