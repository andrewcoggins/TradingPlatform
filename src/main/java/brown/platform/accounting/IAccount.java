package brown.platform.accounting;

import java.util.List;
import java.util.Set;

import brown.mechanism.tradeable.ITradeable;

/**
 * Accounts store agent monies and tradeables.
 * @author andrew
 *
 */
public interface IAccount {
  
  /**
   * @return ID of agent owning the account
   */
  public double getID();

  /**
   * @return double representing amount of money in account
   */
  public double getMonies();
  
  /**
   * @return List of ITtradeables representing goods in account
   */  
  public List<ITradeable> getGoods();
  
  /**
   * Add money to an account
   * @param newMonies - money to be added
   */
  public void add(double newMonies);
  
  /**
   * @param newMonies : add money
   * @param newGoods : add goods 
   */
  public void add(double newMonies, List<ITradeable> newGoods);
  
  public void add(double newMonies, Set<ITradeable> newGoods);
  
  public void add(double newMonies, ITradeable newGood);
  
   /**
   * Remove money from an account
   * @param newMonies - money to be removed
   */
  public void remove(double newMonies);

  /**
   * Removes monies and goods
   * @param removeMonies - money to remove
   * @param removeGoods - goods to remove 
   */
  public void remove(double removeMonies, List<ITradeable> removeGoods);
  
  public void remove(double removeMonies, Set<ITradeable> removeGoods);
   
  public void remove(double removeMonies, ITradeable removeGood);

  /** 
   * clears an account
   */
  public void clear();
  
  /**
   * copies an account
   * @return copied account
   */
  public Account copyAccount();
  
}