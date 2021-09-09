<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Main class method forwarding the stimulus to the concrete state

-->
${tc.signature("stimulus", "className")}
${cd4c.method("public void " + stimulus?uncap_first +"()")}
{
  this.state.handle${stimulus?cap_first}((${className})this);
}
