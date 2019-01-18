package brown.platform.input.config.library;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.input.config.ITradeableConfig;

public class TradeableConfig implements ITradeableConfig {
  
  private String tradeableName; 
  private Constructor<?> tType; 
  private Integer numTradeables; 
  private Constructor<?> valDistribution; 
  private Map<Constructor<?>, List<Double>> generators; 
  
  
  public TradeableConfig(String tradeableName, Constructor<?> tTypeCons, Integer numTradeables, 
      Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators) {
    this.tradeableName = tradeableName; 
    this.tType = tTypeCons; 
    this.numTradeables = numTradeables; 
    this.valDistribution = distCons;
    this.generators = generators; 
    
  }

  @Override
  public String getTradeableName() {
    
    return this.tradeableName;
  }

  @Override
  public Constructor<?> getTType() {
    
    return this.tType;
  }

  @Override
  public Integer getNumTradeables() {
    
    return this.numTradeables;
  }

  @Override
  public Constructor<?> getValDistribution() {
    
    return this.valDistribution;
  }
  
  @Override
  public Map<Constructor<?>, List<Double>> getGenerator() {
    return this.generators;
  }

  @Override
  public String toString() {
    return "TradeableConfig [tradeableName=" + tradeableName + ", tType="
        + tType + ", numTradeables=" + numTradeables + ", valDistribution="
        + valDistribution + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((numTradeables == null) ? 0 : numTradeables.hashCode());
    result = prime * result + ((tType == null) ? 0 : tType.hashCode());
    result = prime * result
        + ((tradeableName == null) ? 0 : tradeableName.hashCode());
    result = prime * result
        + ((valDistribution == null) ? 0 : valDistribution.hashCode());
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
    TradeableConfig other = (TradeableConfig) obj;
    if (numTradeables == null) {
      if (other.numTradeables != null)
        return false;
    } else if (!numTradeables.equals(other.numTradeables))
      return false;
    if (tType != other.tType)
      return false;
    if (tradeableName == null) {
      if (other.tradeableName != null)
        return false;
    } else if (!tradeableName.equals(other.tradeableName))
      return false;
    if (valDistribution == null) {
      if (other.valDistribution != null)
        return false;
    } else if (!valDistribution.equals(other.valDistribution))
      return false;
    return true;
  }
  
}
