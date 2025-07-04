package de.monticore;

import de.monticore.scbasis.SCBasisMill;
import de.monticore.triggeredstatecharts.TriggeredStatechartsMill;
import de.monticore.umlstatecharts.UMLStatechartsMill;
import de.se_rwth.commons.logging.Finding;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.regex.Pattern;

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

  @BeforeEach
  public void setUp() {
    initLogger();
    initUMLStatechartsMill();
  }

  @AfterEach
  public void clearLog() {
    Log.clearFindings();
    LogStub.clearPrints();
  }

  /**
   * We clear the global scope after each test. Don't assume that symbols added
   * before or during a test are available to the next test.
   */
  @AfterEach
  protected void clearGlobalScope() {
    SCBasisMill.globalScope().clear();
  }

  public static String[] getLoggedErrorCodes() {
    return Log.getFindings().stream()
      .filter(Finding::isError)
      .map(Finding::getMsg)
      .map(msg -> msg.substring(0, 7))
      .filter(Pattern.compile("0x[0-9a-fA-F]{5}").asPredicate())
      .toArray(String[]::new);
  }
}
