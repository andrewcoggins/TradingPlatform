package brown.platform.input.config.library;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.generator.IValuationGenerator;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.input.config.ITradeableConfig;

public class TradeableConfig implements ITradeableConfig {
  
  private String tradeableName; 
  private TradeableType tType; 
  private Integer numTradeables; 
  private IValuationDistribution valDistribution; 
  
  
  public TradeableConfig(String tradeableName, TradeableType tType, Integer numTradeables, 
      IValuationDistribution valDistribution) {
    this.tradeableName = tradeableName; 
    this.tType = tType; 
    this.numTradeables = numTradeables; 
    this.valDistribution = valDistribution; 
  }


  @Override
  public String getTradeableName() {
    
    return this.tradeableName;
  }


  @Override
  public TradeableType getTType() {
    
    return this.tType;
  }


  @Override
  public Integer getNumTradeables() {
    
    return this.numTradeables;
  }


  @Override
  public IValuationDistribution getValDistribution() {
    
    return this.valDistribution;
  }
}
