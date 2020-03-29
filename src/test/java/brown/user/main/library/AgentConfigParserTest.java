package brown.user.main.library;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import brown.user.main.IAgentConfig;
import brown.user.main.IAgentConfigParser;

public class AgentConfigParserTest {
  
  @Test
  public void testAgentConfigParse() throws FileNotFoundException,
      ClassNotFoundException, NoSuchMethodException, SecurityException,
      InstantiationException, IllegalAccessException, IllegalArgumentException,
      InvocationTargetException, IOException, ParseException {
    // agent config parser should be able to parse an agent json into a
    // List<IAgentConfig>

    IAgentConfigParser testParser = new AgentConfigParser();
    List<IAgentConfig> agentConfigs =
        testParser.parseConfig("agent_configs/test_agent_input.json");

    List<IAgentConfig> expectedConfigs = new LinkedList<IAgentConfig>();

    expectedConfigs.add(new AgentConfig("agent_class_1", "agent_name_1"));
    expectedConfigs.add(new AgentConfig("agent_class_2", "agent_name_2"));
    expectedConfigs.add(new AgentConfig("agent_class_3", "agent_name_3"));
    expectedConfigs.add(new AgentConfig("agent_class_4", "agent_name_4"));

    assertEquals(agentConfigs, expectedConfigs);

  }

}
