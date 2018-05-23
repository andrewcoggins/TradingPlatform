package brown.server.library;

import java.util.LinkedList;
import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.CombClockWithQueryRules;
import brown.market.preset.library.QueryMarketRules;
import brown.setup.library.SpecValSetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SpecValV2Config;

/**
 * Combinatorial auction server main class, with query.
 * @author kerry
 *
 */
public class CombAuctionWithQueryServer {
  private int initDelay; 
  private int lag;      
  private int port;
  private int nSims;
  private int nQueryRounds;
  private double increment;
  private int initBundles;
  private int initMean;
  private int initStd;
  private String outFile;
  
  public CombAuctionWithQueryServer(int initDelay, int lag, int port, int nSims, int nQueryRounds, double increment,
      int initBundles, int initMean, int initStd, String outFile) {
    this.initDelay = initDelay;
    this.lag = lag;
    this.port = port;
    this.nSims = nSims;
    this.nQueryRounds = nQueryRounds;    
    this.increment = increment;
    this.initBundles = initBundles;
    this.initMean = initMean;
    this.initStd = initStd;
    this.outFile = outFile;
  }
  
  public CombAuctionWithQueryServer(int initDelay, int lag, int nSims, int nQueryRounds, double increment,
      int initBundles, int initMean, int initStd, String outFile){
    this(initDelay, lag, 2121, nSims, nQueryRounds, increment, initBundles, initMean, initStd, outFile);
  }

  public void runAll() throws InterruptedException {                
    // Create _ tradeables
    // Create tradeables
    List<ITradeable> allTradeablesList = new LinkedList<ITradeable>();
    // add tradeables.
    for (int i = 0; i < 98; i++) {
      allTradeablesList.add(new SimpleTradeable(i));
    } 
    
    
    List<AbsMarketPreset> oneMarket = new LinkedList<AbsMarketPreset>();          
    oneMarket.add(new QueryMarketRules(this.nQueryRounds));
    SimulMarkets phase_one = new SimulMarkets(oneMarket);
    
    List<AbsMarketPreset> twoMarket = new LinkedList<AbsMarketPreset>();
    twoMarket.add(new CombClockWithQueryRules(this.increment));    
    SimulMarkets phase_two = new SimulMarkets(twoMarket);
    
    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();  
    seq.add(phase_one);
    seq.add(phase_two);
    
    Simulation testSim = new Simulation(seq,new SpecValV2Config(this.initBundles,this.initMean,this.initStd),
        allTradeablesList,0.0,new LinkedList<ITradeable>());    
    RunServer testServer = new RunServer(port, new SpecValSetup());
    
    testServer.runSimulation(testSim, this.nSims, this.initDelay, this.lag, this.outFile);           
  }
  
  
  public static void main(String[] args) throws InterruptedException {
    PredictionMarketServer server = new PredictionMarketServer(10,20,5,50, "test file");
    server.runAll();
  }
}
