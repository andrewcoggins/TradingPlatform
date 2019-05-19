package brown.auction.value.valuation;

import brown.platform.item.ICart;

/**
 * IValuation specifies valuations for ITradeables. 
 * An IValuation will be specific to a single agent.
 * @author andrew
 *
 */
public interface ISpecificValuation {
  
  /**
   * This method returns a valuation for some ITradeable.
   * @param tradeable an ITradeable.
   * @return the value the agent has for the input tradeable.
   */
  public Double getValuation(ICart cart);
 
}
