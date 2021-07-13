${tc.signature("ast", "printer")}
/**
  * We have added this comment using the ConfigTemplate
  */
<@compress single_line=true>
    ${printer.prettyprint(ast.getModifier())} ${printer.prettyprint(ast.getMCType())} ${ast.getName()}
    <#if ast.isPresentInitial()>=${printer.prettyprint(ast.getInitial())}</#if>;
</@compress>