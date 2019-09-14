package brown.simulations;

import java.util.LinkedList;
import java.util.List;

import brown.logging.library.AuctionLogging;
import brown.logging.library.PlatformLogging;

public class SecondPriceSimulation extends AbsUserSimulation {

  public SecondPriceSimulation(List<String> agentClass, String inputJSON,
      String outFile, boolean writeToFile) {
    super(agentClass, inputJSON, outFile, writeToFile);
  }

  public void run() throws InterruptedException {

    ServerRunnable sr = new ServerRunnable();
    AgentRunnable ar = new AgentRunnable(agentClass.get(0), "s");
    AgentRunnable arTwo = new AgentRunnable(agentClass.get(0), "p");


    Thread st = new Thread(sr);
    Thread at = new Thread(ar);
    Thread atTwo = new Thread(arTwo);

    st.start();
    if (agentClass != null) {
      at.start();
      atTwo.start();
    }

    while (true) {
      if (!st.isAlive()) {
        at.interrupt();
        break;
      }
      Thread.sleep(1000);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    List<String> agentList = new LinkedList<String>();
    agentList.add("brown.user.agent.library.SimpleAgent");
    SecondPriceSimulation basicSim = new SecondPriceSimulation(agentList,
        "input_configs/second_price_auction.json", "outfile", false);
    AuctionLogging.setLogging(true);
    basicSim.run();
  }

}