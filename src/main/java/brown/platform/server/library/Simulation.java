package brown.platform.server.library;

import java.util.List;

import brown.auction.value.manager.library.ValuationManager;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.server.ISimulation;

/**
 * A simulation specifies a sequence of markets that will 
 * be run by the server, as well as information about 
 * valuations, tradeables, and initial endowments.
 * @author acoggins
 *
 */
public class Simulation implements ISimulation {

  private final List<SimulMarkets> sequence;
  private final ValuationManager valInfo;
  private final List<ITradeable> allTradeables;
  private final Double initialMonies;
  private final List<ITradeable> initialGoods;

  public Simulation(List<SimulMarkets> sequence, ValuationManager valInfo, List<ITradeable> tradeables,
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
  public ValuationManager getValInfo() {
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
