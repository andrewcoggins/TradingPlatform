package brown.value.valuation;

import brown.value.valuationrepresentation.IValuationRepresentation;

public interface IDependentValuation extends IValuation {
  
  public IValuationRepresentation getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}