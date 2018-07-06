package brown.platform.server;

import java.util.List;

import brown.auction.preset.AbsMarketPreset;
import brown.auction.value.config.library.ValConfig;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.server.library.Simulation;

public interface IServer {
  
  public void runSimpleSim(List<ITradeable> allGoods, AbsMarketPreset rules,
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag, String outputFile) throws InterruptedException; 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int initLag, int lag, String outputFile) throws InterruptedException; 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int lag, String outputFile) throws InterruptedException;
  
}