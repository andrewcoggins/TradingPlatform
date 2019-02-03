package brown.user.main.library;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.platform.market.IMarketRules;
import brown.user.main.IMarketConfig;

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

  @Override
  public String toString() {
    return "MarketConfig [rules=" + rules + ", numTradeablesMap="
        + numTradeablesMap + ", mustInclude=" + mustInclude + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((mustInclude == null) ? 0 : mustInclude.hashCode());
    result = prime * result
        + ((numTradeablesMap == null) ? 0 : numTradeablesMap.hashCode());
    result = prime * result + ((rules == null) ? 0 : rules.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MarketConfig other = (MarketConfig) obj;
    if (mustInclude == null) {
      if (other.mustInclude != null)
        return false;
    } else if (!mustInclude.equals(other.mustInclude))
      return false;
    if (numTradeablesMap == null) {
      if (other.numTradeablesMap != null)
        return false;
    } else if (!numTradeablesMap.equals(other.numTradeablesMap))
      return false;
    if (rules == null) {
      if (other.rules != null)
        return false;
    } else if (!rules.equals(other.rules))
      return false;
    return true;
  }
  
  
}