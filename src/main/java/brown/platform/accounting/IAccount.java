package brown.platform.accounting;

import java.util.List;

import brown.platform.item.ICart;
import brown.platform.item.IItem;

/**
 * An account stores agent money and items.
 * 
 * @author andrewcoggins
 *
 */
public interface IAccount {

  /**
   * get the agent ID of the account holder
   * 
   * @return agent ID
   */
  public int getID();

  /**
   * get the money in the account
   * 
   * @return the money in the account.
   */
  public double getMoney();

  /**
   * get the goods in the account that correspond to a name
   * 
   * @param name the name of the goods to get. If the agent has 'airplane
   *          tickets', will get tradeables corresponding to airplane tickets.
   * @return
   */
  public IItem getGoods(String name);

  /**
   * get all goods in the account
   * 
   * @return a map from the names of the goods to the tradeables that represent
   *         the goods.
   */
  public ICart getAllGoods();

  /**
   * add tradeables to the account.
   * 
   * @param name the name of the tradeables to add.
   * @param tradeables the tradeables themselves.
   */
  public void addTradeables(IItem itemToAdd);

  /**
   * remove the tradeables from the account.
   * 
   * @param name the name of the tradeables to remove
   * @param tradeables the actual tradeables to be removed.
   */
  public void removeTradeables(IItem itemToRemove);

  /**
   * add money to the account
   * 
   * @param money the money to be added.
   */
  public void addMoney(double money);

  /**
   * remove money from the account.
   * 
   * @param newMoney the money to be removed.
   */
  public void removeMoney(double newMoney);

  /**
   * remove all tradeables and money from the account.
   */
  public void clear();

}
