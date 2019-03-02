package brown.user.main.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.platform.market.IMarketRules;
import brown.user.main.IMarketConfig;

public class MarketConfig implements IMarketConfig {
  
  private IMarketRules rules; 
  private List<String> tradeableNames; 
 
  public MarketConfig(IMarketRules marketRules, 
      List<String> tradeableNames) {
    this.rules = marketRules; 
    this.tradeableNames = tradeableNames;   
  }
  
  @Override
  public IMarketRules getRules() {
    return this.rules;
  }

  @Override
  public List<String> getTradeableNames() { 
    return this.tradeableNames;
  }


  
  
}