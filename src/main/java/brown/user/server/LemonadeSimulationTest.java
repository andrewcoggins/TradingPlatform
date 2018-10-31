package brown.user.server;

import java.util.LinkedList;
import java.util.List;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.LemonadeGroupedRulesAnon;
import brown.auction.value.manager.library.LemonadeConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.MultiTradeable;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.system.setup.library.LemonadeSetup;

/**
 * Test of a lemonade game run.
 * @author kerry
 *
 */
public class LemonadeSimulationTest {
  public static void main(String[] args) throws InterruptedException {
    // Make sure students know this before hand so they can change some constant in their agents or something
    int numSlots = 12;
    // This is for the 3 person case – the rules will automatically normalize for larger groups
    int totalTradeables = 24;
    
    // simulation variables
    int delayTime = 5;
    int lag = 150; // speed at which rounds run - at lag=100, 100 trials takes 50s-60s
    int numSims = 4;
    
    List<ITradeable> allTradeables = new LinkedList<ITradeable>(); 
    allTradeables.add(new MultiTradeable(1, totalTradeables));
       
    List<AbsMarketRules> firstmarket_seq = new LinkedList<AbsMarketRules>();
    firstmarket_seq.add(new LemonadeGroupedRulesAnon(12));
    firstmarket_seq.add(new LemonadeGroupedRulesAnon(50));    
    SimulMarkets firstMarket = new SimulMarkets(firstmarket_seq);

    List<AbsMarketRules> secondmarket_seq = new LinkedList<AbsMarketRules>();
    secondmarket_seq.add(new LemonadeGroupedRulesAnon(12));    
    SimulMarkets secondMarket = new SimulMarkets(secondmarket_seq);
    
    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
//    seq.add(firstMarket);
    seq.add(secondMarket);
    
    Simulation testSim = new Simulation(seq,new LemonadeConfig(),allTradeables,0.,new LinkedList<ITradeable>());    
        
    RunServer testServer = new RunServer(2121, new LemonadeSetup());
    testServer.runSimulation(testSim, numSims, delayTime, lag, null);
  }
}
