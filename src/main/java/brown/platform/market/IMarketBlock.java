package brown.platform.market;

import java.util.List;

import brown.platform.item.ICart;

/**
 * IMarketBlock encapsulates a collection of markets that run simultaneously. 
 * 
 * @author andrewcoggins
 *
 */
public interface IMarketBlock {

  /**
   * Get the markets in the MarketBlock. 
   * 
   * @return
   */
  public List<IFlexibleRules> getMarkets();

  /**
   * Get the carts that are traded in the markets in the MarketBlock.
   * 
   * @return
   */
  public List<ICart> getMarketCarts();

}
