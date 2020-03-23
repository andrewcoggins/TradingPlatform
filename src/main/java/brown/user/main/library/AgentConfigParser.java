package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import brown.user.main.IAgentConfig;
import brown.user.main.IAgentConfigParser;

public class AgentConfigParser implements IAgentConfigParser {

  @Override
  public List<IAgentConfig> parseConfig(String fileName)
      throws FileNotFoundException, IOException, ParseException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException {

    Object rawInput = new JSONParser().parse(new FileReader(fileName));

    JSONObject jo = (JSONObject) rawInput;
    JSONArray ja = (JSONArray) jo.get("agents");

    Iterator agentIterator = ja.iterator();
    Iterator<Map.Entry> agentKeyIterator;

    List<IAgentConfig> aConfigs = new LinkedList<IAgentConfig>();

    // get the strings from the json
    while (agentIterator.hasNext()) {

      String agentClass = "";
      String agentName = "";

      agentKeyIterator = ((Map) agentIterator.next()).entrySet().iterator();
      while (agentKeyIterator.hasNext()) {
        Map.Entry pair = agentKeyIterator.next();
        if (pair.getKey().equals("agentClass")) {
          agentClass = (String) pair.getValue();
        } else if (pair.getKey().equals("agentName")) {
          agentName = (String) pair.getValue();
        }

        IAgentConfig agentConfig = new AgentConfig(agentClass, agentName);
        aConfigs.add(agentConfig);

      }

    }

    return aConfigs;
  }
}
