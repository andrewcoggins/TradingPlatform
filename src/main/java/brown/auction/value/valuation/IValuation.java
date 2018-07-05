package brown.auction.value.valuation;

import brown.mechanism.tradeable.ITradeable;


/**
 * IValuation specifies valuations for ITradeables. 
 * An IValuation will be specific to a single agent.
 * @author andrew
 *
 */
public interface IValuation {
  
  /**
   * This method returns a valuation for some ITradeable.
   * @param tradeable an ITradeable. This may be a simple, 
   * complex or a multitradeable
   * @return the value the agent has for the input tradeable.
   */
  public Double getValuation(ITradeable tradeable);
  
  /**
   * An unfortunate java hack that we'd like to get rid of if we can.
   * @return a copy of this valuation.
   */
  public IValuation safeCopy();
  
}