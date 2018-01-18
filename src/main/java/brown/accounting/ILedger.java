package brown.accounting; 

import java.util.List;

import brown.accounting.library.Ledger;
import brown.accounting.library.Transaction;
import brown.tradeable.ITradeable;

/**
 * A ledger tracks all trades within a Market. 
 * 
 * Not really sure what latest did but I removed it and only kept unshared
 * 
 * @author kerry
 */
public interface ILedger {

  // Add a transaction
  public void add(Transaction t);
  
  // Add list of transactions
  public void add(List<Transaction> trans); 
  
  // Add good,ID,price (like in an auction where an agent buys a good)
  public void add(ITradeable good, Integer agent, Double price);

  // Returns full history of transactions
  public List<Transaction> getList();
  
  // Returns history of transactions
  public List<Transaction> getUnshared();
  
  // Clears unshared transactions
  public void clearUnshared();
  
  // Wipes agent IDs other than this one
  public Ledger getSanitized(Integer agentD);
}