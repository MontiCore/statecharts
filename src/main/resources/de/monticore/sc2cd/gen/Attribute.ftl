<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  Generates a Java attribute

-->

${tc.signature("ast", "printer")}
<@compress single_line=true>
 ${printer.prettyprint(ast.getModifier())} ${printer.prettyprint(ast.getMCType())} ${ast.getName()}
 <#if ast.isPresentInitial()>=${printer.prettyprint(ast.getInitial())}</#if>;
</@compress>
  ${glex.defineHookPoint(tc,"<Statement>*gen.Attribute",ast)}

