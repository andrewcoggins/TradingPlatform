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

public class CallMarketServer {
  private int seconds;
  private int numSims;
  private int initDelay;; 
  private int lag;
  
  public CallMarketServer(int seconds, int numSims, int initDelay, int lag){
    this.seconds = seconds;
    this.numSims = numSims;
    this.initDelay = initDelay;
    this.lag = lag;
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
    RunServer testServer = new RunServer(2121, new CallMarketSetup());
    
    testServer.runSimulation(testSim, this.numSims, this.initDelay, this.lag);           
  }
  
  
  public static void main(String[] args) throws InterruptedException {
    
    CallMarketServer server = new CallMarketServer(30,100,5,50);
    server.runAll();
  }
}
