package brown.user.agent.library;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import brown.communication.messages.IBankUpdateMessage;
import brown.communication.messages.IValuationMessage;
import brown.logging.library.UserLogging;
import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.main.library.Main;
import brown.platform.utils.Utils;

public abstract class AbsFictitiousPlayAgent extends AbsAgent
    implements IAgent {

  public AbsFictitiousPlayAgent(String host, int port, ISetup gameSetup) {
    super(host, port, gameSetup);
  }

  public AbsFictitiousPlayAgent(String host, int port, ISetup gameSetup,
      String name) {
    super(host, port, gameSetup, name);
  }

  /**
   * @param otherAgents map from fictitious agent's name to its class.
   */
  protected void doFictitiousPlay(String meAgentName, String meAgentClass,
      IValuationMessage fpValuation, IBankUpdateMessage fpEndowment,
      Map<String, String> otherAgents) {
    Thread serverThread = new Thread(this.getNewServerRunnable());
    serverThread.start();
    UserLogging.log("Fictitious Play Server Started.");
    // generate the 'me' agent thread
    Thread meAgentThread = new Thread(this.getNewMeAgentRunnable(meAgentClass,
        meAgentName, fpValuation, fpEndowment));
    Map<String, Thread> otherAgentThreads = new HashMap<String, Thread>();
    otherAgents.keySet().forEach(key -> otherAgentThreads.put(key,
        new Thread(this.getNewAgentRunnable(otherAgents.get(key), key))));
    Utils.sleep_helper(1000);
    UserLogging.log("me agent starting");
    meAgentThread.start();
    UserLogging.log("fp agents: " + otherAgentThreads.toString());
    otherAgentThreads.keySet()
        .forEach(key -> otherAgentThreads.get(key).start());
    UserLogging.log("Agent threads started.");
    while (true) {
      if (!serverThread.isAlive()) {
        meAgentThread.interrupt();
        otherAgentThreads.keySet()
            .forEach(key -> otherAgentThreads.get(key).interrupt());
        break;
      }
      Utils.sleep_helper(1000); 
    }
  }

  /**
   * @param otherAgents map from fictitious agent's name to its class.
   */
  public void doFictitiousPlayClasses(Map<String, Class<?>> otherAgents) {
    Thread serverThread = new Thread(this.getNewServerRunnable());
    serverThread.start();
    Map<String, Thread> agentThreads = new HashMap<String, Thread>();
    otherAgents.keySet().forEach(key -> agentThreads.put(key, new Thread(
        this.getNewAgentRunnableWithClass(otherAgents.get(key), key))));
    agentThreads.keySet().forEach(key -> agentThreads.get(key).start());
    while (true) {
      if (!serverThread.isAlive()) {
        agentThreads.keySet().forEach(key -> agentThreads.get(key).interrupt());
        break;
      }
      Utils.sleep_helper(1000);
    }
  }

  private FPServerRunnable getNewServerRunnable() {
    return new FPServerRunnable(this.simulationJsonFileName);
  }

  private FPAgentRunnable getNewAgentRunnable(String agentString,
      String agentName) {
    return new FPAgentRunnable(agentString, agentName);
  }

  private FPAgentRunnable getNewMeAgentRunnable(String agentString,
      String agentName, IValuationMessage valuation,
      IBankUpdateMessage endowment) {
    return new FPAgentRunnable(agentString, agentName, valuation, endowment);
  }

  private FPAgentRunnable getNewAgentRunnableWithClass(Class<?> agentClass,
      String agentName) {
    return new FPAgentRunnable(agentClass, agentName);
  }

  private class FPServerRunnable implements Runnable {

    private String jsonFile;

    public FPServerRunnable(String jsonFile) {
      this.jsonFile = modifyInputJSON(jsonFile);
    }

    @Override
    public void run() {
      String[] inputArgs = new String[1];
      inputArgs[0] = jsonFile;
      try {
        Main.main(inputArgs);
      } catch (ClassNotFoundException | NoSuchMethodException
          | InstantiationException | IllegalAccessException
          | InvocationTargetException | IllegalArgumentException
          | InterruptedException | IOException | ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    private String modifyInputJSON(String jsonFile) {

      Object rawInput;
      try {
        rawInput = new JSONParser().parse(new FileReader(jsonFile));
        JSONObject jo = (JSONObject) rawInput;

        jo.put("learningDelayTime", 0);
        if (jo.containsKey("serverPort")) {
          jo.put("serverPort", (Long) jo.get("serverPort") + 1);
        } else {
          jo.put("serverPort", 2122);
        }

        String newJsonFile = "fictitious_play_config.json";
        FileWriter fileWrite = new FileWriter(newJsonFile);
        fileWrite.write(jo.toJSONString());
        fileWrite.close();
        return newJsonFile;
      } catch (IOException | ParseException e) {
        e.printStackTrace();
      }
      return jsonFile;
    }
  }

  private class FPAgentRunnable implements Runnable {

    private String agentString;
    private Class<?> agentClass;
    private String agentName;
    private String HOST = "localHost";
    private IValuationMessage valuation;
    private IBankUpdateMessage endowment;
    private int PORT = 2122;
    
    /**
     * an agent loaded directly from a class. 
     * @param agentClass
     * @param agentName
     */
    public FPAgentRunnable(Class<?> agentClass, String agentName) {
      this.agentClass = agentClass;
    }
    
    /**
     * an agent loaded from a string.
     * @param agentString
     * @param agentName
     */
    public FPAgentRunnable(String agentString, String agentName) {
      this.agentString = agentString;
      this.agentName = agentName;
    }

    /**
     * a 'me' agent with endowment and valuation.
     * @param agentString
     * @param agentName
     * @param valuation
     * @param endowment
     */
    public FPAgentRunnable(String agentString, String agentName,
        IValuationMessage valuation, IBankUpdateMessage endowment) {
      this.agentString = agentString;
      this.agentName = agentName;
      this.valuation = valuation;
      this.endowment = endowment;
    }

    @Override
    public void run() {
      try {
        Class<?> cl;
        if (agentString != null) {
          cl = Class.forName(agentString);
        } else {
          cl = agentClass;
        }
        if (this.valuation != null && this.endowment != null) {
          Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE,
              ISetup.class, String.class, IValuationMessage.class, IBankUpdateMessage.class);
          cons.newInstance(HOST, PORT, new Setup(), this.agentName,
              this.valuation, this.endowment);
        } else {
          if (this.agentName != null) {
            Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE,
                ISetup.class, String.class);
            cons.newInstance(HOST, PORT, new Setup(), this.agentName);
          } else {
            Constructor<?> cons =
                cl.getConstructor(String.class, Integer.TYPE, ISetup.class);
            cons.newInstance(HOST, PORT, new Setup());
          }
          while (true) {
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

  }
}
