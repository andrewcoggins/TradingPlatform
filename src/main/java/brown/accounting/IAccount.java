package brown.accounting;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import brown.accounting.library.Account;
import brown.tradeable.ITradeable;

public interface IAccount {
  
  public double getID();
  
  public double getMonies();
  
  public List<ITradeable> getGoods();
  
  /**
   * Add money to an account
   * @param newMonies - money to be added
   * @return updated account
   */
  public void add(double newMonies);
  
  /**
   * @param newMonies : add money
   * @param newGoods : add goods 
   * @return updated account
   */
  public void add(double newMonies, List<ITradeable> newGoods);
  
  public void add(double newMonies, Set<ITradeable> newGoods);
  
  public void add(double newMonies, ITradeable newGood);
  
   /**
   * Remove money from an account
   * @param newMonies - money to be removed
   * @return updated account
   */
  public void remove(double newMonies);

  /**
   * Removes monies and goods; leave 0 or null if gives an already constructed account to a particular agent.not using both
   * @param removeMonies - money to remove
   * @param removeGoods - goods to remove 
   * @return updated account
   */
  public void remove(double removeMonies, List<ITradeable> removeGoods);
  
  public void remove(double removeMonies, Set<ITradeable> removeGoods);
   
  /**
   * Removes an individual good and money
   * @param removeMonies - money to be removed
   * @param good - to be removed
   * @return updated account
   */
  public void remove(double removeMonies, ITradeable removeGood);

  /** clears an account
   */
  public void clear();
  
  /**
   * copies an account
   * @return copied account
   */
  public Account copyAccount();
  
}