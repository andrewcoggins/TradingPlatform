package brown.value.valuation;

import brown.value.valuationrepresentation.AbsValuationRepresentation;



public interface IDependentValuation extends IValuation {
  
  public AbsValuationRepresentation getSomeValuations(Integer numValuations, 
      Integer bundleSizeMean, Double bundleSizeStdDev);
  
}