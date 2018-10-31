package brown.user.server; 

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.auction.preset.AbsMarketRules;
import brown.auction.preset.NormalSSFP;
import brown.auction.value.manager.library.AdditiveLab2Config;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.platform.server.library.RunServer;
import brown.platform.server.library.SimulMarkets;
import brown.platform.server.library.Simulation;
import brown.system.setup.library.SSSPSetup;

/**
 * Use this class to run the server side of your game.
 * Just edit the rules to the game that you'd like to play.
 * 
 * This is the only file the server-side user should have to edit.
 * 
 */
public class Lab2Server {
	
	private static int numSims = 5;
	private static int delayTime = 5; 
	private static int lag = 100;

	public static void main(String[] args) throws InterruptedException {
	    // Create _ tradeables
	    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
	    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
	
	    // only 1 tradeable
	    allTradeables.add(new SimpleTradeable(0));
	    allTradeablesList.add(new SimpleTradeable(0));
	    
	    List<AbsMarketRules> oneMarket = new LinkedList<AbsMarketRules>();
	    // PairSSSP for second price, SSFP for first price
	    // Writes out agentID, bid, valuation
	    oneMarket.add(new NormalSSFP("/Users/Kerry/Documents/Brown/HTA_1951K/testFile.csv"));   
	    // oneMarket.add(new PairSSFP(1,"/Users/Kerry/Documents/Brown/HTA_1951K/testFile.csv"));   
	    SimulMarkets markets = new SimulMarkets(oneMarket);
	
	    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
	    seq.add(markets);
	    
	    // AdditiveUniformConfig OR AdditiveLab2Config
	    Simulation testSim = new Simulation(seq, new AdditiveLab2Config(allTradeables),
	    		allTradeablesList, 1., new LinkedList<ITradeable>());    
	    RunServer testServer = new RunServer(2121, new SSSPSetup());
	    
	    testServer.runSimulation(testSim, numSims, delayTime, lag, null);
	}
}
