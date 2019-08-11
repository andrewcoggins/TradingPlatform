package brown.simulations;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.parser.ParseException;

import brown.system.setup.ISetup;
import brown.system.setup.library.Startup;
import brown.user.main.library.Main;

public class BasicSimulation {

  private static String host = "localhost";
  private static int port = 2121;
  private String outFile;
  private String agentClass;
  private String inputJSON;

  
  public BasicSimulation(String agentClass, String inputJSON, String outFile) {
    this.agentClass = agentClass;
    this.inputJSON = inputJSON; 
    this.outFile = outFile;
  }

  public void run() throws InterruptedException {
    
    ServerRunnable sr = new ServerRunnable();
    AgentRunnable ar = new AgentRunnable();

    Thread st = new Thread(sr);
    Thread at = new Thread(ar);
    Thread atTwo = new Thread(ar);

    st.start();
    if (agentClass != null) {
      at.start();
      atTwo.start(); 
    }
    
    while(true) {
      if (!st.isAlive()) {
        at.interrupt();
        break; 
      }
      Thread.sleep(1000);
    }
  }

  private class ServerRunnable implements Runnable {

    private int tier;

    @Override
    public void run() {
      DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
      String outfile =
          outFile + agentClass + tier + "-" + df.format(new Date());
      String[] inputArgs = new String[1];
      inputArgs[0] = inputJSON; 
      
      try {
        Main.main(inputArgs);
      } catch (ClassNotFoundException | NoSuchMethodException
          | InstantiationException | IllegalAccessException
          | InvocationTargetException | IllegalArgumentException
          | InterruptedException | IOException | ParseException e) {
        e.printStackTrace();
      }
    }
  }

  private class AgentRunnable implements Runnable {

    @Override
    public void run() {
      try {
        Class<?> cl = Class.forName(agentClass);
        Constructor<?> cons =
            cl.getConstructor(String.class, Integer.TYPE, ISetup.class);
        cons.newInstance(host, port, new Startup());
        while (true) {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    BasicSimulation basicSim = new BasicSimulation("brown.user.agent.library.SimpleAgent", 
        "input_configs/second_price_auction.json", 
        "outfile"); 
    basicSim.run();
  }
  
}
