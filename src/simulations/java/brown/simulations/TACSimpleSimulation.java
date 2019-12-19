package brown.simulations;

import java.util.LinkedList;
import java.util.List;

public class TACSimpleSimulation extends AbsUserSimulation {

  public TACSimpleSimulation(List<String> agentClass, String inputJSON,
      String outFile, boolean writeToFile) {
    super(agentClass, inputJSON, outFile, writeToFile);
  }

  public void run() throws InterruptedException {

    ServerRunnable sr = new ServerRunnable();
    AgentRunnable ar = new AgentRunnable(agentClass.get(0), "solo");
    AgentRunnable arTwo = new AgentRunnable(agentClass.get(0), "pacifica");
    
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
    agentList.add("brown.user.agent.library.TACAgent");
    TACSimpleSimulation tacSim = new TACSimpleSimulation(agentList,
        "input_configs/TAC_auction_simple.json", "outfile", false);
    tacSim.run();
  }

}