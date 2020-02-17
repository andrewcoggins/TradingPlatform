package brown.logging.library;

import brown.logging.ILogging;

/**
 * Wraps printed messages, so logging can be enabled or disabled.
 * logging.log(X) prints X to console.
 * @author lcamery
 */
public class CommunicationLogging implements ILogging {
  
  // Enable or disable logging here.
  public static boolean COMMUNICATIONLOGGING = false;
  
  /**
   * Logs a message to the console, if logging is set to true.
   * @param message
   */
  public static void log(Object message) {
    if (COMMUNICATIONLOGGING) {
      System.out.println("CommunicationLogging: " + message);
    }
  }
  
  /**
   * set the logging. 
   * @param setLogging
   */
  public static void setLogging(boolean setLogging) {
    COMMUNICATIONLOGGING = setLogging; 
  }

}