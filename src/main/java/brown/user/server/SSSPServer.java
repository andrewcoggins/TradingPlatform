package brown.user.server; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.preset.AbsMarketPreset;
import brown.auction.preset.SSSPRules;
import brown.auction.value.config.library.AdditiveUniformConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.system.setup.library.SSSPSetup;

/**
 * second price auction for multiple goods.
 * @author acoggins
 *
 */
public class SSSPServer {
  
  private static int numSims = 1;
  private static int numTradeables = 1;
  private static int delayTime = 5; 
  private static int lag = 2000;
  
  public static void main(String[] args) throws InterruptedException {
    // Create tradeables
    // TODO: only one collection of tradeables is needed.
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    // add tradeables.
    for (int i = 0; i < numTradeables; i++) {
      allTradeables.add(new SimpleTradeable(new Integer(i)));
      allTradeablesList.add(new SimpleTradeable(new Integer(i)));
    }
    // one market in this game.
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
    oneMarket.add(new SSSPRules());
    SimulMarkets markets = new SimulMarkets(oneMarket);
    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
    seq.add(markets);
    Simulation testSim = new Simulation(seq,new AdditiveUniformConfig(allTradeables),
        allTradeablesList,1.,new LinkedList<ITradeable>());    
    // initialize the server.
    RunServer testServer = new RunServer(2121, new SSSPSetup());
    // run
    testServer.runSimulation(testSim, numSims, delayTime, lag, null);
  }
}