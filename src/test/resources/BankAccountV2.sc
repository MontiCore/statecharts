statechart BankAccountV2 {

  initial state Active;

  Active -> Active [!w]
    / {
        od = 0;
        balance = balance + fb + in;
      }
  Active -> Active [w && balance >= in]
    / {
        od = 0;
        balance = balance + fb - in;
      }

  Active -> Active [w && balance < in]
    / {
        od = in - balance;
        balance = balance + fb - in;
      }
}
