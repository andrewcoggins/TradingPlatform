package brown.server.library;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.library.SSFPNoRecord;
import brown.market.preset.library.SSSPRules;
import brown.market.preset.library.SimpleVCG;
import brown.setup.library.SpecValSetup;
import brown.setup.library.VCGSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SpecValV2Config;

public class TestSpecVal {
  
  //private static int numSims = 1;
  private static int numTradeables = 3;
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
        new SpecValV2Config(), 0.0, new LinkedList<ITradeable>(), delayTime, lag);
  }
}