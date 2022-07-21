<#-- (c) https://github.com/MontiCore/monticore -->
<#--
  This template belongs to StatePatternV3
  
  It delivers a small monitoring of statechart behavior
  by counting method calls;
  
  Type of result: <ClassBodyDecl>*
-->
${tc.signature()}

// counting attribute
protected int count = 0;

public int getCount() {
  return count;
}

protected void intCount() {
  count++;
}

