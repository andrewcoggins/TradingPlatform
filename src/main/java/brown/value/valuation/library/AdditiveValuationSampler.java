package brown.value.valuation.library;

import java.util.Set;

import brown.tradeable.ITradeable;
import brown.value.generator.IValuationGenerator;
import brown.value.valuation.IValuation;
import brown.value.valuation.IValuationSampler;

public class AdditiveValuationSampler implements IValuationSampler {

  @Override
  public IValuation getValuation(IValuationGenerator generator,
      Set<ITradeable> tradeables) {
    return new AdditiveValuation(generator, tradeables);
  }
  
}