package brown.simulations;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.parser.ParseException;

import brown.system.setup.ISetup;
import brown.system.setup.library.Setup;
import brown.user.main.library.Main;

public abstract class AbsUserSimulation {

  protected static String host = "localhost";
  protected static int port = 2121;
  protected String outFile;
  protected List<String> agentClass;
  protected String inputJSON;
  protected boolean writeToFile;

  public AbsUserSimulation(List<String> agentClass, String inputJSON,
      String outFile, boolean writeToFile) {
    this.agentClass = agentClass;
    this.inputJSON = inputJSON;
    this.outFile = outFile;
    this.writeToFile = writeToFile;
  }

  public abstract void run() throws InterruptedException;

  protected class ServerRunnable implements Runnable {

    @Override
    public void run() {
      try {
        if (writeToFile) {
          DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
          String serverOutfile = outFile + "-"
              + inputJSON.replace("input_configs/", "").replace(".json", "") + "-"
              + df.format(new Date()) + ".txt";
          PrintStream out =
              new PrintStream(new FileOutputStream(serverOutfile));
          System.setOut(out);
        }

        String[] inputArgs = new String[1];
        inputArgs[0] = inputJSON;
        Main.main(inputArgs);

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
        if (this.agentName != null) {
          Constructor<?> cons = cl.getConstructor(String.class, Integer.TYPE,
              ISetup.class, String.class);
          cons.newInstance(host, port, new Setup(), this.agentName);
        } else {
          Constructor<?> cons =
              cl.getConstructor(String.class, Integer.TYPE, ISetup.class);
          cons.newInstance(host, port, new Setup());
        }
        while (true) {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
