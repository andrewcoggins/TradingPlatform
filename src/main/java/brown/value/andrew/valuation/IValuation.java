package brown.value.andrew.valuation;

import brown.tradeable.ITradeable;
import brown.value.valuable.library.Value;

/**
 * IValuation specifies valuations for ITradeables. 
 * An IValuation will be specific to a single agent.
 * @author andrew
 *
 */
public interface IValuation {
  
  public Value getValuation(ITradeable tradeable);
  
}