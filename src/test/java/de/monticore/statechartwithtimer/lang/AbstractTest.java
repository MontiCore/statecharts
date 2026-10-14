/* (c) https://github.com/MontiCore/monticore */
package de.monticore.statechartwithtimer.lang;

import de.monticore.scbasis._ast.ASTSCArtifact;
import de.se_rwth.commons.logging.Log;
import de.se_rwth.commons.logging.LogStub;
import org.antlr.v4.runtime.RecognitionException;
import org.junit.jupiter.api.BeforeEach;
import de.monticore.statechartwithtimer.StatechartWithTimerMill;
import de.monticore.statechartwithtimer._parser.StatechartWithTimerParser;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Provides some helpers for tests.
 *
 */
public abstract class AbstractTest {
  
  /** Resets the mill and the log so every test starts from a clean state. */
  @BeforeEach
  public void setUp() {
    StatechartWithTimerMill.reset();
    StatechartWithTimerMill.init();
    LogStub.init();
    Log.enableFailQuick(false);
    Log.getFindings().clear();
  }
  
  /**
   * Parses a model and ensures that the root node is present.
   *
   * @param modelFile the full file name of the model.
   * @return the root of the parsed model.
   */
  protected ASTSCArtifact parseModel(String modelFile) {
    Path model = Paths.get(modelFile);
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    Optional<ASTSCArtifact> optAutomaton;
    try {
      optAutomaton = parser.parse(model.toString());
      assertFalse(parser.hasErrors());
      assertTrue(optAutomaton.isPresent());
      return optAutomaton.get();
    }
    catch (RecognitionException | IOException e) {
      fail("There was an exception when parsing the model " + modelFile + ": " + e.getMessage(), e);
    }
    return null;
  }
  
  protected ASTSCArtifact parseStringModel(String model) {
    StatechartWithTimerParser parser = StatechartWithTimerMill.parser();
    Optional<ASTSCArtifact> optAutomaton;
    try {
      optAutomaton = parser.parse_StringSCArtifact(model);
      assertFalse(parser.hasErrors());
      assertTrue(optAutomaton.isPresent());
      return optAutomaton.get();
    }
    catch (IOException e) {
      fail("There was an exception when parsing the input model: " + e.getMessage(), e);
    }
    return null;
  }
  
}
