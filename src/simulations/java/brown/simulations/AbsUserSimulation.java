package brown.simulations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;

import brown.communication.messageserver.IOnlineMessageServer;
import brown.system.setup.library.Setup;
import brown.user.agent.IAgent;
import brown.user.agent.IAgentBackend;
import brown.user.agent.library.OnlineAgentBackend;
import brown.user.main.library.Main;

public abstract class AbsUserSimulation {

  protected static String host = "localhost";
  protected static int port = 2121;
  protected String outFile;
  protected List<String> agentClass;
  protected String inputJSON;
  protected String agentInputJSON;
  protected boolean writeToFile;

  public AbsUserSimulation(List<String> agentClass, String inputJSON,
      String outFile, boolean writeToFile) {
    this.agentClass = agentClass;
    this.inputJSON = inputJSON;
    this.outFile = outFile;
    this.writeToFile = writeToFile;
    this.agentInputJSON = "";
  }

  public AbsUserSimulation(String agentInputJSON, String serverInputJSON,
      String outFile, boolean writeToFile) {
    this.agentInputJSON = agentInputJSON;
    this.inputJSON = serverInputJSON;
    this.outFile = outFile;
    this.writeToFile = writeToFile;
    this.agentClass = new LinkedList<String>();
  }

  public abstract void run() throws InterruptedException;

  protected class ServerRunnable implements Runnable {

    @Override
    public void run() {
      try {
        if (writeToFile) {
          DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
          String serverOutfile = outFile + "-"
              + inputJSON.replace("input_configs/", "").replace(".json", "")
              + "-" + df.format(new Date()) + ".txt";
          PrintStream out =
              new PrintStream(new FileOutputStream(serverOutfile));
          System.setOut(out);
        }

        if (agentInputJSON.equals("")) {
          String[] inputArgs = new String[1];

          inputArgs[0] = inputJSON;
          Main.main(inputArgs);
        } else {
          String[] inputArgs = new String[2];

          inputArgs[0] = inputJSON;
          inputArgs[1] = agentInputJSON;
          Main.main(inputArgs);
        }

      } catch (ClassNotFoundException | NoSuchMethodException
          | InstantiationException | IllegalAccessException
          | InvocationTargetException | IllegalArgumentException
          | InterruptedException | IOException | ParseException e) {
        e.printStackTrace();
      }
    }
  }

  protected class AgentRunnable implements Runnable {

    private String agentString;
    private String agentName;


    public AgentRunnable(String agentString) {
      this.agentString = agentString;
    }

    public AgentRunnable(String agentString, String agentName) {
      this.agentString = agentString;
      this.agentName = agentName;
    }

    @Override
    public void run() {
      try {
        Class<?> cl = Class.forName(agentString);
        Constructor<?> cons = cl.getConstructor(String.class);
        IAgent agent = (IAgent) cons.newInstance(this.agentName);
        IAgentBackend backend = new OnlineAgentBackend(host, port, new Setup(), agent);
        agent.addAgentBackend(backend);
        while (true) {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
