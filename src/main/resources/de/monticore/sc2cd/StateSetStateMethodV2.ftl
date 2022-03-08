<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Method for the main classes setState

-->
${tc.signature()}
${cd4c.method("public void setState("+ast.getName()+"_State k)")} 
{
  ${glex.defineHookPoint(tc,"<JavaBlock>?StateSetStateMethodV2:begin",ast)}
  this.state = k;
  ${glex.defineHookPoint(tc,"<JavaBlock>?StateSetStateMethodV2:end",ast)}
}
