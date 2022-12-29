<#-- (c) https://github.com/MontiCore/monticore -->
/**
  * We have added this comment using the ConfigTemplate
  */
<@compress single_line=true>
    ${cdPrinter.printModifier(ast.getModifier())} ${cdPrinter.printType(ast.getMCType())} ${ast.getName()}
    <#if ast.isPresentInitial()>=${cdPrinter.printeXPRESSION(ast.getInitial())}</#if>;
</@compress>
