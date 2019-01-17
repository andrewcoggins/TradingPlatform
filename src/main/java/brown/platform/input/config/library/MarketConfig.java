package brown.platform.input.config.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.platform.input.config.IMarketConfig;
import brown.platform.market.IMarketRules;

public class MarketConfig implements IMarketConfig {
  
  private IMarketRules rules; 
  private Map<String, Integer> numTradeablesMap; 
  private Map<String, List<String>> mustInclude; 
 
  public MarketConfig(IMarketRules marketRules, 
      Map<String, Integer> numTradeablesMap, Map<String, List<String>> mustInclude) {
    this.rules = marketRules; 
    this.numTradeablesMap = numTradeablesMap; 
    this.mustInclude = mustInclude;   
  }
  
  public MarketConfig(IMarketRules marketRules,
      Map<String, Integer> numTradeablesMap) {
    this.rules = marketRules; 
    this.numTradeablesMap = numTradeablesMap; 
    this.mustInclude = new HashMap<String, List<String>>(); 
  }

  @Override
  public IMarketRules getRules() {
    return this.rules;
  }

  @Override
  public Map<String, Integer> getNumTradeablesMap() { 
    return this.numTradeablesMap;
  }

  @Override
  public Map<String, List<String>> getMustInclude() {
    return this.mustInclude;
  }
}