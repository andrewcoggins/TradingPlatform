package brown.server;

import java.util.List;

import brown.market.preset.AbsMarketPreset;

public interface ISimulMarkets {
   
  List<AbsMarketPreset> getMarkets();
}
