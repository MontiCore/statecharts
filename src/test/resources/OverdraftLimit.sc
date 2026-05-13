statechart OverdraftLimit {

  initial state Tracking;

  Tracking -> Tracking [w && od > limit]
    / {
        fb = in;
        out = 0;
      }

  Tracking -> Tracking [w && od <= limit]
    / {
        fb = 0;
        out = in;
      }

  Tracking -> Tracking [!w && in > limit]
    / {
        fb = 0;
        out = 0;
        limit = in;
      }

  Tracking -> Tracking [!w && in <= limit]
    / {
        fb = 0;
        out = 0;
      }
}
