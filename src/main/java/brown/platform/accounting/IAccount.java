package brown.platform.accounting;

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
   * Get the agent ID of the account holder.
   * 
   * @return agent ID
   */
  public int getID();

  /**
   * Get the money in the account.
   * 
   * @return the money in the account
   */
  public double getMoney();

  /**
   * Get the named items in the account.
   * 
   * @param name the name of the items to get
   * @return the items in the account that the name references
   */
  public IItem getItems(String name);

  /**
   * Get all the items in the account.
   * 
   * @return a map from the names of the items to the items
   */
  public ICart getAllItems();

  /**
   * Add items to the account.
   * 
   * @param name the name of the items to add
   */
  public void addItems(IItem itemToAdd);

  /**
   * Remove items from the account.
   * 
   * @param name the name of the items to remove
   */
  public void removeItems(IItem itemToRemove);

  /**
   * Add money to the account.
   * 
   * @param money the money to be added
   */
  public void addMoney(double money);

  /**
   * Remove money from the account.
   * 
   * @param newMoney the money to be removed
   */
  public void removeMoney(double newMoney);

  /**
   * Remove all items and all money from the account.
   */
  public void clear();

}
