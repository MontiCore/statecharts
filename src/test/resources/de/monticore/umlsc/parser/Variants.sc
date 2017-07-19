package de.monticore.umlsc.parser;

(...) <<default_invariant = "OCL">>
statechart for Variants {

  (c) initial state s1 {
    [a==5]
    entry / {System.out.println("EntryAction");} [a.assertEquals(5)]
    do [d] / {System.out.println("DoAction");}
    exit / {System.out.println("ExitAction");}
  }

  (c) final state s2
  
  (...) <<state_specific_stereotypes>> state s3 {
    [c]
    state sub1
    final state sub2
    <<intern_transition>> -> [true] internmethod() / {log("intern");} [true]
    sub1 -> sub2 [b==3]
  }

  (c) state s4
  
  state s5 {
    -> return(a==5)
  }
  
  state s6 {
    -> [true] internmethod() / {log("intern");} [true]
  }

  <<transition_specific_stereotypes>> s1 -> s3 event1()
  s3 -> s2 [para1 >= 2] event2(para1, "hello") / {System.out.println("s3->s2");} [event=="postcondition"]
  s1 -> s3 event3
  s1 -> s4 / {a++;} [a>0]
  s1 -> s4 / {a++;}
  s4 -> s2
  s4 -> s2 return
  s4 -> s2 return / {System.out.println("s4->s2");}
  s4 -> s2 return 
  s4 -> s2 return (1+3)
  s4 -> s2 return (1+4) / {System.out.println("s4->s2");}
}
