package brown.value.valuation;

import java.util.Map;

import brown.tradeable.library.ComplexTradeable;

public interface ISpecValValuation extends IValuation{
  
  public Double queryValue(ComplexTradeable t);
  
  public Map<ComplexTradeable, Double> generateXORBids(int nBids, int meanSize, int stdev);

}
