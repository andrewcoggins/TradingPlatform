package brown.server.library;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.CallMarket;
import brown.market.preset.library.PredictionMarketSettlement;
import brown.setup.library.CallMarketSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.PredictionMarketDecoysConfig;

public class PredictionMarketServer {
  private int seconds;
  private int numSims;
  private int initDelay; 
  private int lag;
  
  private int port;
  
  public PredictionMarketServer(int seconds, int numSims, int initDelay, int lag, int port) {
	  this.seconds = seconds;
	  this.numSims = numSims;
	  this.initDelay = initDelay;
	  this.lag = lag;
	  this.port = port;
  }
  
  public PredictionMarketServer(int seconds, int numSims, int initDelay, int lag){
	  this(seconds, numSims, initDelay, lag, 2121);
  }

  public void runAll() throws InterruptedException {                
    // Create _ tradeables
    Set<ITradeable> allTradeables = new HashSet<ITradeable>(); 
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();

    // only 1 tradeable
    allTradeables.add(new SimpleTradeable(0));
    allTradeablesList.add(new SimpleTradeable(0));
    
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();          
    oneMarket.add(new CallMarket(this.seconds));        
    SimulMarkets phase_one = new SimulMarkets(oneMarket);
    
    List<AbsMarketPreset> twoMarket = new LinkedList<AbsMarketPreset>();
    twoMarket.add(new PredictionMarketSettlement());    
    SimulMarkets phase_two = new SimulMarkets(twoMarket);
    
    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
    seq.add(phase_one);
    seq.add(phase_two);
    
    Simulation testSim = new Simulation(seq,new PredictionMarketDecoysConfig(),
        allTradeablesList,0.0,new LinkedList<ITradeable>());    
    RunServer testServer = new RunServer(port, new CallMarketSetup());
    
    testServer.runSimulation(testSim, this.numSims, this.initDelay, this.lag);           
  }
  
  
  public static void main(String[] args) throws InterruptedException {
    PredictionMarketServer server = new PredictionMarketServer(10,5,5,50);
    server.runAll();
  }
}
