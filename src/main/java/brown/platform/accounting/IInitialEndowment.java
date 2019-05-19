package brown.platform.accounting;

import brown.platform.item.ICart;

/**
 * Initial endowment is the items and money that the agent begins a simulation
 * with.
 * 
 * @author andrewcoggins
 *
 */
public interface IInitialEndowment {

  /**
   * get the money associated with the endowment.
   * 
   * @return money
   */
  public double getMoney();

  /**
   * get the goods associated with the endowment.
   * 
   * @return goods
   */
  public ICart getGoods();

}
