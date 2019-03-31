package brown.user.main.library;

import java.util.List;

import brown.platform.market.IFlexibleRules;
import brown.user.main.IMarketConfig;

public class MarketConfig implements IMarketConfig {
  
  private IFlexibleRules rules; 
  private List<String> tradeableNames; 
 
  public MarketConfig(IFlexibleRules marketRules, 
      List<String> tradeableNames) {
    this.rules = marketRules; 
    this.tradeableNames = tradeableNames;   
  }
  
  @Override
  public IFlexibleRules getRules() {
    return this.rules;
  }

  @Override
  public List<String> getTradeableNames() { 
    return this.tradeableNames;
  }

  @Override
  public String toString() {
    return "MarketConfig [rules=" + rules + ", tradeableNames=" + tradeableNames
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((rules == null) ? 0 : rules.hashCode());
    result = prime * result
        + ((tradeableNames == null) ? 0 : tradeableNames.hashCode());
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
    if (rules == null) {
      if (other.rules != null)
        return false;
    } else if (!rules.equals(other.rules))
      return false;
    if (tradeableNames == null) {
      if (other.tradeableNames != null)
        return false;
    } else if (!tradeableNames.equals(other.tradeableNames))
      return false;
    return true;
  } 
  
}