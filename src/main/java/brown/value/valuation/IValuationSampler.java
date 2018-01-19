package brown.value.valuation;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.IValuationGenerator;

public interface IValuationSampler {
  
  public IValuation getValuation(IValuationGenerator generator, Set<ITradeable> tradeables);
  
}