package brown.value.valuation.library; 

import java.util.Map;
import java.util.Set;

import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;
import brown.value.valuation.IBundleValuation;
import brown.value.valuation.IValuation;

public class BundleValuation implements IBundleValuation {

  private final Map<ComplexTradeable, Double> valueParams; 
  
  public BundleValuation(Map<ComplexTradeable, Double> valueParams) {
    this.valueParams = valueParams; 
  }
  
  @Override
  public Double getValuation(ITradeable tradeable) {
    return this.valueParams.get(tradeable);
  }
  
  public Set<ComplexTradeable> getTradeables() { 
    return this.valueParams.keySet();
  }

  @Override
  public IValuation safeCopy() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
}