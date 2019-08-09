package brown.simulations;

import java.lang.reflect.Constructor;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    st.start();
    if (agentClass != null) {
      at.start();
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
    
    // TODO: get this running. 
    @Override
    public void run() {
      DateFormat df = new SimpleDateFormat("MM.dd.yyyy '-' HH:mm:ss");
      String outfile =
          outFile + agentClass + tier + "-" + df.format(new Date());
      PredictionMarketServer server = new PredictionMarketServer(marketLength,
          numSims, delayTime, lag, port + tier, outfile);
      try {
        server.runAll();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  private class AgentRunnable implements Runnable {

    private int tier;

    @Override
    public void run() {
      try {
        Class<?> cl = Class.forName(agentClass);
        Constructor<?> cons =
            cl.getConstructor(String.class, Integer.TYPE, String.class);
        cons.newInstance(host, port + tier, agentClass);

        while (true) {
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) throws InterruptedException {
    BasicSimulation basicSim = new BasicSimulation("agentClass", "outputJSON", "outfile"); 
    basicSim.run();
  }
  
}
