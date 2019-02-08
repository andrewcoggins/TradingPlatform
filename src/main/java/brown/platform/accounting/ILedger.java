package brown.platform.accounting; 

import java.util.List;

import brown.platform.accounting.library.Ledger;
import brown.platform.accounting.library.Transaction;
import brown.platform.tradeable.ITradeable;

/**
 * A ledger tracks all trades within a Market. 
 * 
 * @author Kerry
 */
public interface ILedger {

  /**
   * @param t - Transaction to add to the ledger
   */
  public void add(Transaction t);
  
  /**
   * @param trans - List of transactions to add to the ledger
   */
  public void add(List<Transaction> trans); 
  
  /**
   * Add a transaction to ledger with just good,ID,and price (e.g. when agent
   * buys a good from auctioneer)
   * 
   * @param good  
   * @param agent - agentID
   * @param price
   */
  public void add(ITradeable good, Integer agent, Double price);

  /**
   * @return - List of transactions
   */
  public List<Transaction> getList();
  
  /**
   * @return - List of transactions yet to be shared over kryo
   */
  public List<Transaction> getUnshared();
  
  /**
   * @param agentID - Agent ID to keep in ledger (wipes other agent IDs)
   * @return Ledger containing unshared transactions
   */
  public Ledger getSanitizedUnshared(Integer agentID);
  
  /**
   *  Clears unshared transactions
   */
  public void clearUnshared();
  
}