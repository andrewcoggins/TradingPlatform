package brown.platform.accounting;

import brown.platform.accounting.library.Transaction;
import brown.platform.item.ICart;

/**
 * transaction describes a change made to an account
 * 
 * @author andrewcoggins
 *
 */
public interface ITransaction {

  /**
   * remove private details from the transaction (the agent's private ID)
   * 
   * @param agentID the agent ID to be sanitized
   * @return a sanitized transaction
   */
  public Transaction sanitize(Integer agentID);
  
  public Integer getTo(); 
  
  public Integer getFrom(); 
  
  public Double getCost(); 
  
  public ICart getCart(); 
  
  /**
   * the direction
   * @return
   */
  public boolean receiveCart(); 
}
