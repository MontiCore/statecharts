package tool;

import de.se_rwth.commons.logging.Log;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by
 *
 * @author KH
 */
public class SCToolTest {
  
  @BeforeClass
  public static void disaBleFailQuick(){
    Log.enableFailQuick(false);
  }
  
  @Test
  public void executeMain() {
    SCTool.main(new String[] { "src/test/models/Banking.sc" });
    
    assertTrue(!false);
  }
  
}
