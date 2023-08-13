<!-- (c) https://github.com/MontiCore/monticore -->

# MontiCore Transformations



This page describes the model transformation capabilities of MontiCore, also known as MontiTrans.
Explicit examples and snippets are based on the MontiCore [Statechart language](https://github.com/MontiCore/statecharts).
MontiCore transformation support is achieved by multiple facets:
The MontiCore tool is capable of generating (domain-specific and compositional) *transformation grammars* from MontiCore grammars.
The model transformations themselves are written as models of this transformation language 
and using an also generated language-specific *transformation generator tool* can be converted to Java source files.
A more in-depth description is given in
[MontiTrans: Agile, modellgetriebene Entwicklung von und mit domänenspezifischen, kompositionalen Transformationssprachen](https://www.se-rwth.de/phdtheses/Diss-Hoelldobler-MontiTrans-Agile-modellgetriebene-Entwicklung-von-und-mit-domaenenspezifischen-kompositionalen-Transformationssprachen.pdf)

## Transformation Grammar and Generator Generation
By executing the MontiCore generator with standard options, the corresponding transformation 
grammar with the suffix `TR` is generated.
```
java -jar monticore.jar -g Automata.mc4 -mp monticore-rt.jar
```

Aftwards, the MontiCore generator must be executed on the generated `TR` grammar again (with the `genDSTL` option) 
to generate sources for the transformation generator tool.
```bash
java -jar monticore.jar -g out/tr/AutomataTR.mc4 -mp monticore-rt.jar -genDST=true
```

Hand-written extensions to the transformation grammar are possible.

Finally, to compile the language-specific Transformation Generator Tool (TFGenTool), execute the following command:
```bash
javac -cp monticore-rt.jar -sourcepath "src/;out/;hwc/" out/tr/AutomataTFGenTool.java
```

Please note, on Unix systems paths are separated using ":" (colon)
instead of semicolons.

## Writing Transformations
Transformations are specified as models of a concrete transformation language and usually stored in `.mtr` files.
They offer four main concepts: **Pattern Matching**, **Replacement**, **Negation**, and **Lists**.

Transformations utilize the same *concrete syntax* as the original language to allow the matching of patterns, 
thus, providing modelers with a familiar syntax for specifying model transformations.
The following transformation matches on an automaton named PingPong with a state named MyState.
```
automaton PingPong {
  state MyState;
}
```


A *replacement operator* allows the replacement/removal/creation of elements. ``[[ old :- new]]``

For example, the following transformation renames a state MyState to MyOtherState in an automaton named MyAut:
```
automaton MyAut {
  state [[ MyState :- MyOtherState ]];
}
```
By leaving the left or right side of the replacement operator empty, creation or removal can be achieved.


Instead of using identifiers, *variables* may be used. They start with a dollar sign(`$`). An anonymous variable `$_` is also offered.
For example, the following transformation renames a state specified by the variable `$oldName` to MyOtherState in any named automaton:
```
automaton $_ {
  state [[ $oldName :- MyOtherState ]];
}
```

Instead of using the concrete syntax, it is also possible to use the abstract syntax within a transformation by using the name of the nonterminal of the element to be matched,
followed by a variable and a semicolon.
For example, the following transformation removes states within a named automaton.
```
automaton $_ {
    [[ State $S; :-  ]];
}
```

To further constrain the matching it is possible to use a *where-block* containing a (Java) expression evaluating to a boolean value nested in curly brackets.
For example, the following transformation removes states whose name starts with My.
```
automaton $_ {
    [[ State $S; :-  ]]
}
where {
  $S.getName().startsWith("My")
}
```

Negation allows for specifying elements that must not occur in order to match. They start with the keyword `not` followed by their syntax nested in double square brackets.
For instance, the following transformation removes states whose name starts with My in an automaton without a state named Immutable.
```
automaton $_ {
    [[ State $S; :- ]]
  not [[ state Immutable; ]]
}
where {
  $S.getName().startsWith("My")
}
```

Mixing concrete and abstract syntax is also possible by specifying the abstract part followed by the concrete part in double square brackets.
For example, the following transformation matches a state named MyState and captures it into the variable $S.
```
automaton $_ {
  State $S [[ state MyState ]];
}
```

To combine Java method calls and transformations, there exist *do-* and *undo-blocks*.
The expressions within them are executed when the transformation is applied (or reversed).
The following exemplary transformation renames all states by appending the current date.
```
automaton $_ {
  State $S;
}
do {
  $S.setName($S.getName() + NamingUtils.getCurrentDate());
}
```

<!-- MontiCore and MontiTrans add much more transformation capabilities, 
such as assignments, foldings, lists, specific (first, last, relative,...) replacement conditions, ... -->



## Transformation Generation
Consider the following *AddNewState.mtr* transformation file:
```
automaton $_ {
    [[ :- state $S; ]]
}
```


To generate Java source code from a transformation model, the TFGenTool can be used:
```bash
java -cp "monticore-rt.jar;./out;./src;./hwc" tr.AutomataTFGenTool -i AddNewState.mtr
```

```bash
javac -cp "monticore-rt.jar;./out;./src;./hwc" -sourcepath ./out out/de/monticore/tf/AddNewState.java
```

## Applying Transformations (Java)
The newly generated transformation Java class can either be used in other Java source code, such as the following:
```java
AddNewState newStateTrafo = new de.monticore.tf.AddNewState(ast);
if (newStateTrafo.doPatternMatching())
    newStateTrafo.doReplacement();
```

## Applying Transformations (Tool)
Additionally, the already generated language tool may be used.
Using transformation workflow files written in groovy, we can write complex workflows applying transformations.

**Note:** The transformation workflow option has to be added manually to the languages project files-refer to the statecharts for an example.
The current MontiCore example does not yet support this.
You can follow the examples by substituting the monticore-rt runtime with the *UMLSCTransGenerator* and the *de.monticore.tr.UMLStatechartsTFGenTool*.
Please ensure you also run the correct language-specific TFGenTool.

To run the tool with a given transformation workflow script on a model, the following can be used:
```bash
java -cp "monticore-rt.jar;./out;./src;./hwc" AutomataTool -i Door.sc -t AddStateWorkflow.groovy -pp
```

For example to use the `AddNewstate` transformation and bind the `$S` variable to `"MyNewState"`.
```groovy
addNewState(ast, [S: "MyNewState"])
```

Each transformation method returns the matched variables (or false in case it was unable to match).
The following example repeats the AddNewTransitionFromAny transformation and prints the element captured in the `$from` variable.
```groovy
// Create transitions from every state to MyNewState
while (m = addNewTransitionFromAny(ast, [to: "MyNewState"])) {
  println "Adding transition from " + m.get_$from()
}
```

With AddNewTransitionFromAny looking like:
```mtr
automaton  $_ {
  state $from; // free variable
  state $to; // make sure to not have $to -> $to
  not [[ $from -> $to; ]]
  [[ :- $from -> $to; ]]
}
```


Transformations from other packages can be simply imported using the `import` keyword:

```groovy
import my.pack.TrafoName;
import my.other.pack.*;
```

In case multiple transformations with the same name (but in different packages) are used, 
the `withTransformation(fqn, alias)` method can be used to set an alias.

The third and final parameter of each transformation function is an optional boolean.
If setting it to false, no replacement will be done (and only the match or false returned).

## Further Information

* [Project root: MontiCore @github](https://github.com/MontiCore/monticore)
* [MontiCore documentation](http://www.monticore.de/)
* [**List of languages**](https://github.com/MontiCore/monticore/blob/dev/docs/Languages.md)
* [**MontiCore Core Grammar Library**](https://github.com/MontiCore/monticore/blob/dev/monticore-grammar/src/main/grammars/de/monticore/Grammars.md)
* [Best Practices](https://github.com/MontiCore/monticore/blob/dev/docs/BestPractices.md)
* [Publications about MBSE and MontiCore](https://www.se-rwth.de/publications/)
* [Licence definition](https://github.com/MontiCore/monticore/blob/master/00.org/Licenses/LICENSE-MONTICORE-3-LEVEL.md)

