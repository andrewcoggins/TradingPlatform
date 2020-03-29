package brown.user.main.library;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;

import brown.user.main.IAgentConfig;
import brown.user.main.IAgentConfigParser;
import brown.user.main.IServerConfigParser;
import brown.user.main.ISimulationConfig;

/**
 * the main class of TradingPlatform. Start a simulation by running this class.
 * 
 * @author acoggins
 *
 */
public class Main {

  /**
   * 
   * @param args
   * @throws ClassNotFoundException
   * @throws NoSuchMethodException
   * @throws InstantiationException
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws IllegalArgumentException
   * @throws InterruptedException
   * @throws FileNotFoundException
   * @throws IOException
   * @throws ParseException
   */
  public static void main(String[] args) throws ClassNotFoundException,
      NoSuchMethodException, InstantiationException, IllegalAccessException,
      InvocationTargetException, IllegalArgumentException, InterruptedException,
      FileNotFoundException, IOException, ParseException {
    String serverConfigFileName = args[0];
    List<ISimulationConfig> configs = new LinkedList<>();
    IServerConfigParser serverParser = new ServerConfigParser(); 
    
    List<ISimulationConfig> runConfig = serverParser.parseConfig(serverConfigFileName);
    configs.addAll(runConfig);
    
    Map<String, Integer> outerParams =
        serverParser.parseServerConfigParameters(serverConfigFileName);
    Map<String, Double> doubleParams =
        serverParser.parseServerConfigDoubleParameters(serverConfigFileName);
    Integer startingDelayTime = outerParams.get("startingDelayTime");
    Integer learningDelayTime = outerParams.get("learningDelayTime");
    Double simulationDelayTime = doubleParams.get("simulationDelayTime");
    Integer numTotalRuns = outerParams.get("numTotalRuns");
    Integer serverPort = outerParams.get("serverPort");
    
    ConfigRun configRun; 
    if (args.length > 1) {
      String agentConfigFileName = args[1]; 
      IAgentConfigParser agentParser = new AgentConfigParser(); 
      List<IAgentConfig> agentConfigs = agentParser.parseConfig(agentConfigFileName); 
      // if agent config, automatically default to offline mode. 
      // if no agent config, cannot be in offline mode. 
      
      // An FP Agent can, in the outer scope, be offline, but must be offline in the inner scope. 
      // Online or offline agents can launch offline simulations/agents, but cannot launch online agents. 
      
      // all 'simulations' are now to be offline. 
      
      configRun = new ConfigRun(configs, agentConfigs);
    } else {
      configRun = new ConfigRun(configs);
    }
    
    
    configRun.run(startingDelayTime, simulationDelayTime, learningDelayTime,
        numTotalRuns, serverPort, serverConfigFileName);
  }

}
