package brown.server.library; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.PairSSFP;
import brown.market.preset.library.PairSSSP;
import brown.market.preset.library.SSSPRules;
import brown.setup.library.SSSPSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.AdditiveLab2Config;
import brown.value.config.AdditiveUniformConfig;

/**
 * Use this class to run the server side of your game.
 * Just edit the rules to the game that you'd like to play.
 * 
 * This is the only file the server-side user should have to edit.
 * 
 */
public class Lab2Server {

  public static void main(String[] args) throws InterruptedException {
    // Create _ tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();

    // only 1 tradeable
    allTradeables.add(new SimpleTradeable(0));
    allTradeablesList.add(new SimpleTradeable(0));
    
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();
    // PairSSSP for second price, SSFP for first price
    oneMarket.add(new PairSSFP(1));    
    SimulMarkets markets = new SimulMarkets(oneMarket);

    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
    seq.add(markets);
    
    // AdditiveUniformConfig OR AdditiveLab2Config
    Simulation testSim = new Simulation(seq,new AdditiveLab2Config(allTradeables),allTradeablesList,1.,new LinkedList<ITradeable>());    
    RunServer testServer = new RunServer(2121, new SSSPSetup());
    
    int numSims = 3;
    int delayTime = 5; 
    int lag = 100;
    
    testServer.runSimulation(testSim, numSims, delayTime, lag);
  }
}