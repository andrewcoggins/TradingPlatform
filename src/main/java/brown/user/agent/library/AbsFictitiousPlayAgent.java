package brown.user.agent.library;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;

import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.main.library.Main;

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
  protected void doFictitiousPlay(Map<String, String> otherAgents) {
    Thread serverThread = new Thread(this.getNewServerRunnable());
    serverThread.start();
    Map<String, Thread> agentThreads = new HashMap<String, Thread>();
    otherAgents.keySet().forEach(key -> agentThreads.put(key,
        new Thread(this.getNewAgentRunnable(otherAgents.get(key), key))));
    agentThreads.keySet().forEach(key -> agentThreads.get(key).start());
    while (true) {
      if (!serverThread.isAlive()) {
        agentThreads.keySet().forEach(key -> agentThreads.get(key).interrupt());
        break;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  /**
   * @param otherAgents map from fictitious agent's name to its class.
   */
  public void doFictitiousPlayClasses(Map<String, Class<?>> otherAgents) {
    Thread serverThread = new Thread(this.getNewServerRunnable());
    serverThread.start();
    Map<String, Thread> agentThreads = new HashMap<String, Thread>();
    otherAgents.keySet().forEach(key -> agentThreads.put(key,
        new Thread(this.getNewAgentRunnableWithClass(otherAgents.get(key), key))));
    agentThreads.keySet().forEach(key -> agentThreads.get(key).start());
    while (true) {
      if (!serverThread.isAlive()) {
        agentThreads.keySet().forEach(key -> agentThreads.get(key).interrupt());
        break;
      }
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private FPServerRunnable getNewServerRunnable() {
    return new FPServerRunnable(this.simulationJsonFileName);
  }

  private FPAgentRunnable getNewAgentRunnable(String agentString,
      String agentName) {
    return new FPAgentRunnable(agentString, agentName);
  }
  
  private FPAgentRunnable getNewAgentRunnableWithClass(Class<?> agentClass,
      String agentName) {
    return new FPAgentRunnable(agentClass, agentName);
  }

  private class FPServerRunnable implements Runnable {

    private String jsonFile;

    public FPServerRunnable(String jsonFile) {
      this.jsonFile = jsonFile;
    }

    @Override
    public void run() {
      // TODO Auto-generated method stub
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

  }

  private class FPAgentRunnable implements Runnable {

    private String agentString;
    private Class<?> agentClass;
    private String agentName;
    private String HOST = "localHost";
    private int PORT = 2122;

    public FPAgentRunnable(Class<?> agentClass, String agentName) {
      this.agentClass = agentClass;
    }

    public FPAgentRunnable(String agentString, String agentName) {
      this.agentString = agentString;
      this.agentName = agentName;
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
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
