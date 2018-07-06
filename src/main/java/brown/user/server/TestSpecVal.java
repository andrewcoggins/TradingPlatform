package brown.user.server;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.preset.SSFPNoRecord;
import brown.auction.value.config.library.SpecValV2Config;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.server.library.RunServer;
import brown.system.setup.library.SpecValSetup;

/**
 * test for a specval auction.
 * @author acoggins
 *
 */
public class TestSpecVal {
  
  //private static int numSims = 1;
  private static int numTradeables = 100;
  private static int delayTime = 5; 
  private static int lag = 300;
  
  public static void main(String[] args) throws InterruptedException {
    // Create tradeables
    // TODO: only one collection of tradeables is needed.
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    // add tradeables.
    for (int i = 0; i < numTradeables; i++) {
      allTradeables.add(new SimpleTradeable(i));
      allTradeablesList.add(new SimpleTradeable(i));
    }
    // initialize the server.
    RunServer gameServer = new RunServer(2121, new SpecValSetup());
    // run
    //TODO: make agent
    //TODO: debug
    gameServer.runSimpleSim(allTradeablesList, new SSFPNoRecord(3),
        new SpecValV2Config(50,10,2), 0.0, new LinkedList<ITradeable>(), delayTime, lag, null);
  }
}