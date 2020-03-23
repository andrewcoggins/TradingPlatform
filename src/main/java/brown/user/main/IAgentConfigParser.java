package brown.user.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.simple.parser.ParseException;

public interface IAgentConfigParser extends IInputParser {
  
  public List<IAgentConfig> parseConfig(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException;
  
}
