package brown.value.valuation;

import brown.tradeable.ITradeable;


/**
 * IValuation specifies valuations for ITradeables. 
 * An IValuation will be specific to a single agent.
 * @author andrew
 *
 */
public interface IValuation {
  
  public Double getValuation(ITradeable tradeable);
  
  public IValuation safeCopy();
  
}