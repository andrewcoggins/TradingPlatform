package brown.user.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.simple.parser.ParseException;

/**
 * parses an agent JSON
 * @author andrewcoggins
 *
 */
public interface IAgentConfigParser extends IInputParser {
  
  /**
   * Parse an agent JSON
   * @param fileName
   * @return
   * List<IAgentConfig>
   */
  public List<IAgentConfig> parseConfig(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException;
  
}
