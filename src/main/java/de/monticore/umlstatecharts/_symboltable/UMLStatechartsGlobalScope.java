/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts._symboltable;

import com.google.common.collect.Sets;
import de.monticore.io.paths.ModelPath;
import de.monticore.utils.Names;

import java.util.Set;

public class UMLStatechartsGlobalScope extends UMLStatechartsGlobalScopeTOP {
  
  UMLStatechartsGlobalScope realThis;
  
  public UMLStatechartsGlobalScope(ModelPath modelPath, String fileExt) {
    super(modelPath, fileExt);
    this.realThis = this;
  }
  
  public UMLStatechartsGlobalScope() {
    this.realThis = this;
  }
  
  @Override public UMLStatechartsGlobalScope getRealThis() {
    return this.realThis;
  }
  
  @Override
  public Set<String> calculateModelNamesForSCState(String name) {
    if(name.contains(".")){
      return Sets.newHashSet(Names.getQualifier(name), name);
    }
    return Sets.newHashSet(name);
  }
}
