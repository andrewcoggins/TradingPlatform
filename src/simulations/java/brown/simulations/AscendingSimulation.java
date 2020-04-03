package brown.simulations;

public class AscendingSimulation extends AbsUserSimulation {

  public AscendingSimulation(String agentInputJSON, String inputJSON,
      String outFile, boolean writeToFile) {
    super(agentInputJSON, inputJSON, outFile, writeToFile);
  }

  public void run() throws InterruptedException {

    ServerRunnable sr = new ServerRunnable();
    Thread st = new Thread(sr);

    st.start();

  }

  public static void main(String[] args) throws InterruptedException {
    new OfflineSimulation("agent_configs/ascending_agent_test_config.json",
        "input_configs/english_auction_example.json", "outfile", false).run();
  }

  
}
