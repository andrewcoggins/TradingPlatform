package brown.server.library; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SSFPNoRecord;
import brown.server.RunServer;
import brown.server.SimulMarkets;
import brown.server.Simulation;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.AdditiveUniformConfig;

/**
 * runs a simple simultaneous first price auction.
 * @author acoggins
 *
 */
public class SSFPServer { 
  
  private static int numSims = 5;
  private static int numTradeables = 7;
  private static int delayTime = 5; 
  private static int lag = 300;
  
  public static void main(String[] args) throws InterruptedException {
    // Create tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    // add tradeables.
    for (int i = 0; i < numTradeables; i++) {
      allTradeables.add(new SimpleTradeable(i));
      allTradeablesList.add(new SimpleTradeable(i));
    }
    // one market in this game.
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
    oneMarket.add(new SSFPNoRecord(1));
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