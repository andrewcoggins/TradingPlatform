package brown.user.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

/**
 * parse server JSON. 
 * @author andrewcoggins
 *
 */
public interface IServerConfigParser extends IInputParser {

  /**
   * parse server JSON config. 
   * @param fileName
   * @return
   */
  public List<ISimulationConfig> parseConfig(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException;
  
  /**
   * parse server config outer parameters. 
   */
  public Map<String, Integer> parseServerConfigParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException;
  
  /**
   * parse server config parameters that are doubles. 
   * @param fileName
   * @return
   */
  public Map<String, Double> parseServerConfigDoubleParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException; 

}
