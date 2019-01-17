package brown.logging.library;

import brown.logging.ILogging;

/**
 * Wraps printed messages, so logging can be enabled or disabled.
 * logging.log(X) prints X to console.
 * @author acoggins
 */
public class TestLogging implements ILogging {
  
  // Enable or disable logging here.
  public final static boolean TESTLOGGING = true;
  
  /**
   * Logs a message to the console, if logging is set to true.
   * @param message
   */
  public static void log(String message) {
    if (TESTLOGGING) {
      System.out.println("[x] TEST: " + message);
    }
  }

  public static void log(Integer message) {
    if (TESTLOGGING) {
      System.out.println("[x] TEST: " + message);
    }
  }

  public static void log(double message) {
    if (TESTLOGGING) {
      System.out.println("[x] TEST: " + message);
    }
  }
  
}