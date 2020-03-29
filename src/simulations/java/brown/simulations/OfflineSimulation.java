package brown.simulations;

public class OfflineSimulation extends AbsUserSimulation {

  public OfflineSimulation(String agentInputJSON, String inputJSON,
      String outFile, boolean writeToFile) {
    super(agentInputJSON, inputJSON, outFile, writeToFile);
  }

  public void run() throws InterruptedException {

    ServerRunnable sr = new ServerRunnable();
    Thread st = new Thread(sr);

    st.start();

  }

  public static void main(String[] args) throws InterruptedException {
    new OfflineSimulation("agent_configs/simple_agent_config.json",
        "input_configs/offline_basic_simulation.json", "outfile", false).run();
  }

}
