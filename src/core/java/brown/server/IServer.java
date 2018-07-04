package brown.server;

import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

public interface IServer {
  
  public void runSimpleSim(List<ITradeable> allGoods, AbsMarketPreset rules,
      ValConfig valInfo, double initialMonies, List<ITradeable> initialGoods, int delay, int lag, String outputFile) throws InterruptedException; 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int initLag, int lag, String outputFile) throws InterruptedException; 
  
  public void runSimulation(Simulation sim, int numRuns, int delay, int lag, String outputFile) throws InterruptedException;
  
}