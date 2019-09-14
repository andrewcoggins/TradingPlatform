package brown.logging.library;

import brown.logging.ILogging;

/**
 * Wraps printed messages, so logging can be enabled or disabled.
 * logging.log(X) prints X to console.
 * @author lcamery
 */
public class SystemLogging implements ILogging {
  
  // Enable or disable logging here.
  public final static boolean SYSTEMLOGGING = false;
  
  /**
   * Logs a message to the console, if logging is set to true.
   * @param message
   */
  public static void log(Object message) {
    if (SYSTEMLOGGING) {
      System.out.println(message);
    }
  }

}