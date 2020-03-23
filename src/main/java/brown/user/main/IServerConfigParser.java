package brown.user.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

public interface IServerConfigParser extends IInputParser {

  public List<ISimulationConfig> parseConfig(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException;
  
  public Map<String, Integer> parseServerConfigParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException;
  
  public Map<String, Double> parseServerConfigDoubleParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException; 

}
