/* (c) https://github.com/MontiCore/monticore */

package de.monticore.umlsc.parser;

statechart for SimpleClass {

/*
state A {

  state B {
  
    state C;
  }
}

state D;
D -> A.B.C;
*/

/*
state N {

  state A;

  state B {
    state C {
      state D {
        state X {
          
          state Z;
        }
      }  
      state E;
      D -> E;
      E -> D.X.Z;
    }
  }
  
  state F;
  state G;
  
  G -> B.C;
  B.C -> F;
  G -> B.C.D;
  B.C.E -> F;
}
*/
/*
// state A {

 state D {
   state F {
    state E;
   }
 }
 
 state C {
   state A;
   state B;
   A -> B;
 }
*/

//}

/*
  state A {
   state B;
   state C;
   B -> C;
  }
  
  state D;
  
*/  

/*
  state C {
    state A;
  }
*/  
/*
  state A;
  state B;
  state D;
  state E;
  
  A -> B;
*/
/*
  state A;
  
  state B {
   state C;
  }
  
  A -> B;
*/

/*  
  state A;
  
  state B {
   state C;
  }
  
  B.C -> A;
*/

/*
  state H {
  
    state C {
      state B {
        state A;
      }
    }
  
    state G {
      state F {
        state E {
          state D;
        }
      }
    }
    
    G.F.E.D -> C.B;
  }   
*/
}
