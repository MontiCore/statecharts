/*
 * ******************************************************************************
 * MontiCore Language Workbench, www.monticore.de
 * Copyright (c) 2017, MontiCore, All rights reserved.
 *
 * This project is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this project. If not, see <http://www.gnu.org/licenses/>.
 * ******************************************************************************
 */

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
