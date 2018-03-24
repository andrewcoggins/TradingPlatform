package brown.server.library;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SSSPRules;
import brown.market.preset.library.SimpleVCG;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.AdditiveUniformConfig;
import brown.value.config.SpecVCGValConfig;

public class VCGServer {
  
  private static int numSims = 5;
  private static int numTradeables = 7;
  private static int delayTime = 3; 
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
    RunServer gameServer = new RunServer(2121, new SSSPSetup());
    // run
    //TODO: fix distribution where tradeables have different IDs. 
    //TODO: make agent
    //TODO: debug
    gameServer.runSimpleSim(allTradeablesList, new SimpleVCG(1),
        new SpecVCGValConfig(allTradeables, 10, 5, 2), 0.0, new LinkedList<ITradeable>(), delayTime, lag);
  }
}