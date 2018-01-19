package brown.value.valuation;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

public interface IDependentValuation extends IValuation {
  
  public Map<ITradeable, Value> getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}