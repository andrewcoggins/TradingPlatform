package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import brown.user.main.IJsonParser;
import brown.user.main.ISimulationConfig;

public class JsonParserTest {

  @Test
  public void testJSONParse() throws FileNotFoundException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, ParseException {
    IJsonParser testParser = new JsonParser();
    List<ISimulationConfig> simulationConfigs = testParser.parseJSON("input_configs/test_input.json");
    System.out.println(simulationConfigs.toString()); 
  }

}
