/* (c) https://github.com/MontiCore/monticore */
package de.monticore.umlstatecharts._symboltable;

import de.monticore.io.paths.ModelPath;

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
  
  public  void loadFileForModelName (String modelName)  {
    java.util.Optional<de.monticore.io.paths.ModelCoordinate> mc =
        de.monticore.io.FileFinder.findFile(getModelPath(), modelName, getFileExt(), cache);
    if(mc.isPresent()){
      addLoadedFile(mc.get().getQualifiedPath().toString());
      IUMLStatechartsArtifactScope as = getSymbols2Json().load(mc.get().getLocation());
      addSubScope(as);
    }
  }
}
