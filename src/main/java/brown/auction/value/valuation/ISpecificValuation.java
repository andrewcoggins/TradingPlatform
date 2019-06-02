package brown.auction.value.valuation;

import brown.platform.item.ICart;

/**
 * IValuation specifies valuations for ICarts. 
 * An IValuation will be specific to a single agent.
 * @author andrew
 */
public interface ISpecificValuation {
  
  /**
   * This method returns a valuation for some ICart.
   * @param cart an ICart.
   * @return the value the agent has for the input cart.
   */
  public Double getValuation(ICart cart);
 
}
