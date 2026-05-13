statechart BankAccountV1 {

  initial state Active;

  Active -> Active
    [!w]
    / {
      balance = balance + in;
      out = 0;
    }

  Active -> Active
    [w && balance >= in]
    / {
      balance = balance - in;
      out = in;
    }

  Active -> Active
    [w && balance < in]
    / {
      out = 0;
    }
}
