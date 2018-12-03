package brown.logging.library;

import brown.logging.ILogging;

/**
 * Wraps printed messages, so logging can be enabled or disabled.
 * logging.log(X) prints X to console.
 * @author lcamery
 */
public class ErrorLogging implements ILogging {
  
  // Enable or disable logging here.
  public final static boolean ERRORLOGGING = true;
  
  /**
   * Logs a message to the console, if logging is set to true.
   * @param message
   */
  public static void log(String message) {
    if (ERRORLOGGING) {
      System.out.println(message);
    }
  }

}
