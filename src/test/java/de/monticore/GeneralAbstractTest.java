package de.monticore;

import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.Before;

public abstract class GeneralAbstractTest {
    protected void initLogger() {
        LogStub.init();
        Log.enableFailQuick(false);
    }

    protected void initUMLStatechartsMill() {
        UMLStatechartsMill.reset();
        UMLStatechartsMill.init();
    }

    protected void initTriggeredStatechartsMill() {
        TriggeredStatechartsMill.reset();
        TriggeredStatechartsMill.init();
    }

    @Before
    public void setUp() throws Exception {
        initLogger();
        initUMLStatechartsMill();
    }
}
