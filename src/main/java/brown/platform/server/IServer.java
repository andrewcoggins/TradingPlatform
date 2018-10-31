package brown.platform.server;

import java.util.List;

import brown.auction.preset.AbsMarketRules;
import brown.auction.value.manager.library.ValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.server.library.Simulation;

public interface IServer {
  
  public void runSimpleSim(List<ITradeable> allGoods, AbsMarketRules rules,
                           ValuationManager valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag, String outputFile) throws InterruptedException;
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int initLag, int lag, String outputFile) throws InterruptedException; 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int lag, String outputFile) throws InterruptedException;
  
}