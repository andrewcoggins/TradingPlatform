package brown.logging;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

public class FileLogging implements ILogging {
  
  // Enable or disable logging here.
  public final static boolean LOGGING = true;
  
/**
 * writes a map to an output file. 
 * @param message
 * @throws IOException 
 */
  public static void log(Map<Integer, Double> aMap, String filePath) throws IOException {
    if (LOGGING) {
      PrintWriter out = new PrintWriter(new FileWriter(filePath));
      for (Entry<Integer, Double> anEntry : aMap.entrySet()) {
        out.println(anEntry.getKey() + ", " + anEntry.getValue());
      }
      out.close();
    }
  }
}