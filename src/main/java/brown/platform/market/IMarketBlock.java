package brown.platform.market;

import java.util.List;

import brown.platform.item.ICart;

/**
 * IMarketBlock encapsulates a collection of markets that are handled simultaneously by 
 * the platform. 
 * @author andrewcoggins
 *
 */
public interface IMarketBlock {

  /**
   * Get the markets in the block. 
   * @return
   */
  public List<IFlexibleRules> getMarkets();

  /**
   * get the carts that are traded on in the markets in the MarketBlock
   * @return
   */
  public List<ICart> getMarketCarts();

}
