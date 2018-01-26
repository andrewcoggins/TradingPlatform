package brown.server.library;

import java.util.List;

import brown.market.preset.AbsMarketPreset;
import brown.server.ISimulMarkets;

public class SimulMarkets implements ISimulMarkets{

  private final List<AbsMarketPreset> markets;
  
  public SimulMarkets(List<AbsMarketPreset> markets){
    this.markets = markets;
  }
  
  public List<AbsMarketPreset> getMarkets(){
    return this.markets;
  }
}
