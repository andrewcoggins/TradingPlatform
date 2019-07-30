package brown.platform.accounting;

import brown.platform.accounting.library.Transaction;
import brown.platform.item.ICart;

/**
 * ITransaction describes a change made to an account
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
  
  /**
   * get subject of the transaction
   * @return
   */
  public Integer getTo(); 
  
  /**
   * get the sender of the transaction, if applicable. 
   * @return
   */
  public Integer getFrom(); 
  
  /**
   * get the amount of money exchanged in the transaction
   * @return
   */
  public Double getCost(); 
  
  /**
   * get the ICart exchanged in the transaction
   * @return
   */
  public ICart getCart(); 
  
  /**
   * specifies whether the agent received the cart and lost the money, or lost the 
   * cart and received the money. 
   * @return
   */
  public boolean receiveCart(); 
}
