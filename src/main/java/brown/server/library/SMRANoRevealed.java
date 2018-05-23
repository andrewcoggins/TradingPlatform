package brown.server.library;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.market.preset.AbsMarketPreset;
import brown.market.preset.library.SMRANoRevealedRules;
import brown.market.preset.library.SSSPReserveRules;
import brown.setup.library.SMRASetup;
import brown.tradeable.ITradeable;
import brown.tradeable.library.SimpleTradeable;
import brown.value.config.SMRAConfig;

/**
 * Main server for a SMRA auction with no revealed preference activity rule.
 * not fully functional.
 * @author acoggins
 *
 */
public class SMRANoRevealed {
  
  private static int numSims = 1;
  private static int numTradeables = 7;
  private static int delayTime = 4; 
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
    // construct sequence.
    List<SimulMarkets> seq = new LinkedList<SimulMarkets>();
    //construct price discovery rounds.
    List<AbsMarketPreset> discoveryMarkets = new LinkedList<AbsMarketPreset>(); 
    // TODO: add base and increments.
    Map<ITradeable, Double> baseVals = new HashMap<ITradeable, Double>(); 
    Map<ITradeable, Double> increments = new HashMap<ITradeable, Double>(); 
    for (int i = 0; i < numTradeables; i++) {
      baseVals.put(allTradeablesList.get(i), 0.0); 
      if (i == 4) {
        increments.put(allTradeablesList.get(i), 0.15);
      } else {
        increments.put(allTradeablesList.get(i), 0.2);
      }
    }
    discoveryMarkets.add(new SMRANoRevealedRules(baseVals, increments)); 
    SimulMarkets discovery = new SimulMarkets(discoveryMarkets); 
    seq.add(discovery);
    //construct settlement round.
    List<AbsMarketPreset> settlementMarket = new LinkedList<AbsMarketPreset>(); 
    settlementMarket.add(new SSSPReserveRules(1)); 
    SimulMarkets settlement = new SimulMarkets(settlementMarket); 
    seq.add(settlement); 
    // initialize the simulation.
    Simulation sim = new Simulation(seq, new SMRAConfig(allTradeables),
        allTradeablesList, 1., new LinkedList<ITradeable>()); 
    RunServer smraServer = new RunServer(2121, new SMRASetup()); 
    smraServer.runSimulation(sim, numSims, delayTime, lag, null); 
  }
}