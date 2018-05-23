package brown.server.library;

import java.util.List;

import brown.server.ISimulation;
import brown.tradeable.ITradeable;
import brown.value.config.ValConfig;

/**
 * A simulation specifies a sequence of markets that will 
 * be run by the server, as well as information about 
 * valuations, tradeables, and initial endowments.
 * @author acoggins
 *
 */
public class Simulation implements ISimulation {

  private final List<SimulMarkets> sequence;
  private final ValConfig valInfo;
  private final List<ITradeable> allTradeables;
  private final Double initialMonies;
  private final List<ITradeable> initialGoods;

  public Simulation(List<SimulMarkets> sequence, ValConfig valInfo, List<ITradeable> tradeables,
      Double initialMonies, List<ITradeable> initialGoods){
    this.sequence = sequence;
    this.valInfo = valInfo;
    this.allTradeables = tradeables;     
    this.initialMonies = initialMonies;
    this.initialGoods = initialGoods;
  } 
  
  @Override
  public List<SimulMarkets> getSequence() {
    return this.sequence;
  }
  
  @Override
  public ValConfig getValInfo() {
    return this.valInfo;
  }
  
  @Override
  public List<ITradeable> getTradeables() {
    return this.allTradeables;    
  }  
  
  @Override
  public double getInitialMonies() {
    return this.initialMonies;
  }
  
  @Override
  public List<ITradeable> getInitialGoods(){
    return this.initialGoods;
  }

}
