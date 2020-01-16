package brown.user.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

public interface IJsonParser extends IInputParser {

  public List<ISimulationConfig> parseJSON(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException;

  public Map<String, Integer> parseJSONOuterParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException;
  
  public Map<String, Double> parseJSONDoubleParameters(String fileName)
      throws FileNotFoundException, IOException, ParseException; 

}
