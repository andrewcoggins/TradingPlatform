package brown.platform.accounting;

import brown.platform.accounting.library.Transaction;
import brown.platform.item.ICart;

/**
 * ITransaction describes a change made to an account.
 * 
 * @author andrewcoggins
 *
 */
public interface ITransaction {

  /**
   * Remove private details from the transaction
   * (e.g., the agent's private ID).
   * 
   * @param agentID the agent ID to be sanitized
   * @return a sanitized transaction
   */
  public Transaction sanitize(Integer agentID);
  
  /**
   * Get the recipient of the transaction.
   * 
   * @return
   */
  public Integer getTo(); 
  
  /**
   * Get the sender of the transaction, if applicable.
   * 
   * @return
   */
  public Integer getFrom(); 
  
  /**
   * Get the amount of money exchanged in the transaction.
   * 
   * @return
   */
  public Double getCost(); 
  
  /**
   * Get the ICart exchanged in the transaction.
   * 
   * @return
   */
  public ICart getCart(); 
  
  /**
   * Specifies whether the agent takes the ICart and gives the money, 
   * or gives the ICart and takes the money.
   *
   * @return
   */
  public boolean receiveCart(); 
}
