/* (c) https://github.com/MontiCore/monticore */

statechart Example {
   initial state Some;
    
   state Other { 
       initial state Inner;
   };
   Some -> Other;
}
