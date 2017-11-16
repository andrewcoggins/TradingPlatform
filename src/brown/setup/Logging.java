package brown.setup;

/**
 * wraps printed messages, so messages can be enabled or disabled.
 * logging.log(X) prints X to console.
 * @author lcamery
 *
 */
public class Logging {
	
  //enable or disable logging here.
	public final static boolean LOGGING = true;
	
	/**
	 * logs a String message to the console, if logging is set to true.
	 * @param message
	 */
	public static void log(String message) {
		if (LOGGING) {
			System.out.println(message);
		}
	}

}
