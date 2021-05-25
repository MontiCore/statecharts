${tc.signature("stimulus", "className", "targetStateAttrName")}
${cd4c.method("public void handle${stimulus?cap_first}( ${className} k)")}
{
 // ocl invariant;
 if (true) { // prec
    // action

    // Set next state
    k.setState(k.${targetStateAttrName?uncap_first});
 }
}
