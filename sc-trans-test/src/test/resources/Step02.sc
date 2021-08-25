statechart Step02 {
  state A{
    -> [pre] ev / {foo();} ;
  };
  state B{
    -> ev / {foo();} ;
  };
  state C{
    -> [pre] ev / {foo();};
  };
  state D{
    -> [pre] / {foo();} ;
  };
  state E{
    -> [pre] ev / ;
  };
  state F{
    -> [pre] ev / {} ;
  };
  state G{
    -> / ;
  };

  state H{
    -> [pre] ev / {foo();} ;
    -> [pre2] ev2 / {foo2();} ;
    -> [pre3] /;
  };


}