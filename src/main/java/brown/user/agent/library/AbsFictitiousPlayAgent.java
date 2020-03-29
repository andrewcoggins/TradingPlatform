package brown.user.agent.library;
//
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//import brown.communication.messages.IBankUpdateMessage;
//import brown.communication.messages.IValuationMessage;
//import brown.logging.library.UserLogging;
//import brown.platform.utils.Utils;
//import brown.system.setup.ISetup;
//import brown.system.setup.library.Setup;
//import brown.user.agent.IAgentBackend;
//import brown.user.main.library.Main;
//
public class AbsFictitiousPlayAgent {
  
}
//public abstract class AbsFictitiousPlayAgent extends OnlineAgentBackend
//    implements IAgentBackend {
//
//  public AbsFictitiousPlayAgent(String host, int port, ISetup gameSetup) {
//    super(host, port, gameSetup);
//  }
//
//  public AbsFictitiousPlayAgent(String host, int port, ISetup gameSetup,
//      String name) {
//    super(host, port, gameSetup, name);
//  }
//
//  /**
//   * generate the threads for fictitious play
//   * 
//   * @param meAgentName
//   * @param meAgentClass
//   * @param fpValuation
//   * @param fpEndowment
//   * @param otherAgents
//   * @param newSimulationDelayTime
//   * @return
//   */
//  protected Map<String, Runnable> generateFPRunnables(String meAgentName,
//      String meAgentClass, IValuationMessage fpValuation,
//      IBankUpdateMessage fpEndowment, Map<String, String> otherAgents,
//      double newSimulationDelayTime, int numLearningRuns) {
//    Map<String, Runnable> allFPRunnables = new HashMap<String, Runnable>();
//    // generate server runnables
//    allFPRunnables.put("serverRunnable",
//        this.getNewServerRunnable(newSimulationDelayTime, numLearningRuns));
//    // generate the 'me' agent thread
//    Thread meAgentThread = new Thread(this.getNewMeAgentRunnable(meAgentClass,
//        meAgentName, fpValuation, fpEndowment));
//    allFPRunnables.put("meAgentRunnable", meAgentThread);
//    // generate all other agent threads.
//    otherAgents.keySet().forEach(key -> allFPRunnables.put(key,
//        this.getNewAgentRunnable(otherAgents.get(key), key)));
//    return allFPRunnables;
//  }
//
//  /**
//   * generate the threads for offline fictitious play.
//   * 
//   * @param meAgentName
//   * @param meAgentClass
//   * @param otherAgents
//   * @param newSimulationDelayTime
//   * @return
//   */
//  protected Map<String, Runnable> generateFPThreadsOffline(String meAgentName,
//      String meAgentClass, Map<String, String> otherAgents,
//      double newSimulationDelayTime, int numLearningRuns) {
//    Map<String, Runnable> allFPRunnables = new HashMap<String, Runnable>();
//    // generate server runnables
//    allFPRunnables.put("serverRunnable",
//        this.getNewServerRunnable(newSimulationDelayTime, numLearningRuns));
//    // generate the 'me' agent thread
//    Thread meAgentThread =
//        new Thread(this.getNewAgentRunnable(meAgentClass, meAgentName));
//    allFPRunnables.put("meAgentRunnable", meAgentThread);
//    // generate all other agent threads.
//    otherAgents.keySet().forEach(key -> allFPRunnables.put(key,
//        this.getNewAgentRunnable(otherAgents.get(key), key)));
//    return allFPRunnables;
//  }
//
//  /**
//   * runs fictitious play for the FP agent.
//   * 
//   * @param FPThreads map from ficitious play strings to threads.
//   */
//  protected void doFictitiousPlay(Map<String, Runnable> FPRunnables) {
//    
//    Map<String, Thread> FPThreads = new HashMap<String, Thread>(); 
//    FPRunnables.keySet().forEach(key -> FPThreads.put(key, new Thread(FPRunnables.get(key))));
//    FPThreads.get("serverRunnable").start();
//    UserLogging.log("Server thread started.");
//    Utils.sleep(1000);
//    for (String key : FPThreads.keySet()) {
//      if (key != "serverRunnable")
//        FPThreads.get(key).start();
//    }
//    UserLogging.log("Agent threads started.");
//
//    while (true) {
//      if (!FPThreads.get("serverRunnable").isAlive()) {
//        for(String key : FPThreads.keySet()) {
//          if (key != "serverRunnable") {
//            FPThreads.get(key).interrupt();
//          }
//        }
//        break;
//      }
//      Utils.sleep(1000);
//    }
//    UserLogging.log("fictitious play over"); 
//  }
//
//  // /**
//  // * @param otherAgents map from fictitious agent's name to its class.
//  // */
//  // public void doFictitiousPlayClasses(Map<String, Class<?>> otherAgents) {
//  // Thread serverThread = new Thread(this.getNewServerRunnable());
//  // serverThread.start();
//  // Map<String, Thread> agentThreads = new HashMap<String, Thread>();
//  // otherAgents.keySet().forEach(key -> agentThreads.put(key, new Thread(
//  // this.getNewAgentRunnableWithClass(otherAgents.get(key), key))));
//  // agentThreads.keySet().forEach(key -> agentThreads.get(key).start());
//  // while (true) {
//  // if (!serverThread.isAlive()) {
//  // agentThreads.keySet().forEach(key -> agentThreads.get(key).interrupt());
//  // break;
//  // }
//  // Utils.sleep_helper(1000);
//  // }
//  // }
//
//  private FPServerRunnable getNewServerRunnable(double newSimulationDelayTime, int numLearningRuns) {
//    return new FPServerRunnable(this.simulationJsonFileName,
//        newSimulationDelayTime, numLearningRuns);
//  }
//
//  private FPAgentRunnable getNewAgentRunnable(String agentString,
//      String agentName) {
//    return new FPAgentRunnable(agentString, agentName);
//  }
//
//  private FPAgentRunnable getNewMeAgentRunnable(String agentString,
//      String agentName, IValuationMessage valuation,
//      IBankUpdateMessage endowment) {
//    return new FPAgentRunnable(agentString, agentName, valuation, endowment);
//  }
//
//  // private FPAgentRunnable getNewAgentRunnableWithClass(Class<?> agentClass,
//  // String agentName) {
//  // return new FPAgentRunnable(agentClass, agentName);
//  // }
//
//  private class FPServerRunnable implements Runnable {
//
//    private String jsonFile;
//
//    public FPServerRunnable(String jsonFile, double newSimulationDelayTime, int numLearningRuns) {
//      this.jsonFile = modifyInputJSON(jsonFile, newSimulationDelayTime, numLearningRuns);
//    }
//
//    @Override
//    public void run() {
//      String[] inputArgs = new String[1];
//      inputArgs[0] = jsonFile;
//      try {
//        Main.main(inputArgs);
//      } catch (ClassNotFoundException | NoSuchMethodException
//          | InstantiationException | IllegalAccessException
//          | InvocationTargetException | IllegalArgumentException
//          | InterruptedException | IOException | ParseException e) {
//        e.printStackTrace();
//      }
//    }
//
//    /**
//     * modify some of the outer parameters of the input JSON to facilitate
//     * learning.
//     * 
//     * @param jsonFile the input json file name.
//     * @param newSimulationDelayTime the new simulation delay time.
//     * @return the filename of the new json to be used for the simulation.
//     */
//    private String modifyInputJSON(String jsonFile,
//        double newSimulationDelayTime, int numLearningRuns) {
//
//      Object rawInput;
//      try {
//        rawInput = new JSONParser().parse(new FileReader(jsonFile));
//        JSONObject jo = (JSONObject) rawInput;
//
//        jo.put("learningDelayTime", 0);
//        jo.put("startingDelayTime", 2);
//        jo.put("newSimulationDelayTime", newSimulationDelayTime);
//        jo.put("numTotalRuns", numLearningRuns);
//        if (jo.containsKey("serverPort")) {
//          jo.put("serverPort", (Long) jo.get("serverPort") + 1);
//        } else {
//          jo.put("serverPort", 2122);
//        }
//
//        String newJsonFile = "fictitious_play_config.json";
//        FileWriter fileWrite = new FileWriter(newJsonFile);
//        fileWrite.write(jo.toJSONString());
//        fileWrite.close();
//        return newJsonFile;
//      } catch (IOException | ParseException e) {
//        e.printStackTrace();
//      }
//      return jsonFile;
//    }
//  }
//
//  private class FPAgentRunnable implements Runnable {
//
//    private String agentString;
//    private Class<?> agentClass;
//    private String agentName;
//    private String HOST = "localHost";
//    private IValuationMessage valuation;
//    private IBankUpdateMessage endowment;
//    private int PORT = 2122;
//
//    /**
//     * an agent loaded directly from a class.
//     * 
//     * @param agentClass
//     * @param agentName
//     */
//    public FPAgentRunnable(Class<?> agentClass, String agentName) {
//      this.agentClass = agentClass;
//    }
//
//    /**
//     * an agent loaded from a string.
//     * 
//     * @param agentString
//     * @param agentName
//     */
//    public FPAgentRunnable(String agentString, String agentName) {
//      this.agentString = agentString;
//      this.agentName = agentName;
//    }
//
//    /**
//     * a 'me' agent with endowment and valuation.
//     * 
//     * @param agentString
//     * @param agentName
//     * @param valuation
//     * @param endowment
//     */
//    public FPAgentRunnable(String agentString, String agentName,
//        IValuationMessage valuation, IBankUpdateMessage endowment) {
//      this.agentString = agentString;
//      this.agentName = agentName;
//      this.valuation = valuation;
//      this.endowment = endowment;
//    }
//
//    @Override
//    public void run() {
//      try {
//        Class<?> cl;
//        if (agentString != null) {
//          cl = Class.forName(agentString);
//        } else {
//          cl = agentClass;
//        }
//        if (this.valuation != null && this.endowment != null) {
//          Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE,
//              ISetup.class, String.class, IValuationMessage.class,
//              IBankUpdateMessage.class);
//          cons.newInstance(HOST, PORT, new Setup(), this.agentName,
//              this.valuation, this.endowment);
//        } else {
//          if (this.agentName != null) {
//            Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE,
//                ISetup.class, String.class);
//            cons.newInstance(HOST, PORT, new Setup(), this.agentName);
//          } else {
//            Constructor<?> cons =
//                cl.getConstructor(String.class, Integer.TYPE, ISetup.class);
//            cons.newInstance(HOST, PORT, new Setup());
//          }
//          while (true) {
//          }
//        }
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    }
//
//  }
//}
