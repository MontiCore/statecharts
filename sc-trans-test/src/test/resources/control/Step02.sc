statechart Step02 {
  state A{
    initial final state A_intern;
      A_intern -> A_intern [pre] ev / {foo();};
  };
  state B{
    initial final state B_intern;
      B_intern -> B_intern ev / {foo();} ;
  };
  state C{
    initial final state C_intern;
      C_intern -> C_intern [pre] ev / {foo();};
  };
  state D{
    initial final state D_intern;
      D_intern -> D_intern [pre] / {foo();} ;
  };
  state E{
    initial final state E_intern;
      E_intern -> E_intern [pre] ev /;
  };
  state F{
    initial final state F_intern;
      F_intern -> F_intern [pre] ev / {} ;
  };
  state G{
    initial final state G_intern;
      G_intern -> G_intern /;
  };

  state H{
      H_intern -> H_intern [pre] ev / {foo();} ;
    H_intern -> H_intern [pre2] ev2 / {foo2();} ;
    initial final state H_intern;
    H_intern -> H_intern [pre3] / ;
  };


}