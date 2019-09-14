package brown.simulations.systemtests;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import brown.simulations.BasicSimulation;

public class BasicSimulationTest {

  @Test
  public void testBasicSimulation() throws InterruptedException {
    List<String> agentList = new LinkedList<String>();
    agentList.add("brown.user.agent.library.SimpleAgent");
    BasicSimulation basicSim = new BasicSimulation(agentList,
        "input_configs/second_price_auction.json", "outfile", true);
    basicSim.run();
  }
}
