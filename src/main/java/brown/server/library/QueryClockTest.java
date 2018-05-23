package brown.server.library;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.library.CombClockWithQueryRules;
import brown.setup.library.SpecValSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SpecValV2Config;

public class QueryClockTest {
  
  //private static int numSims = 1;
  private static int numTradeables = 100;
  private static int delayTime = 5; 
  private static int lag = 50;
  
  public static void main(String[] args) throws InterruptedException {
    // Create tradeables
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    // add tradeables.
    for (int i = 0; i < numTradeables; i++) {
      allTradeablesList.add(new SimpleTradeable(i));
    }
    // initialize the server.
    RunServer gameServer = new RunServer(2121, new SpecValSetup());
    // run
    gameServer.runSimpleSim(allTradeablesList, new CombClockWithQueryRules(5.),
        new SpecValV2Config(50,10,2), 0.0, new LinkedList<ITradeable>(), delayTime, lag, null);
  }
}