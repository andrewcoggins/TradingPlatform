package brown.platform.accounting;

import brown.platform.item.ICart;

/**
 * object to specify an update to an account resulting from an auction.
 * 
 * @author andrewcoggins
 *
 */
public interface IAccountUpdate {

  /**
   * converts the IAccountUpdate to an ITransaction
   * 
   * @return an ITransaction reflecting the IAccountUpdate
   */
  public ITransaction toTransaction();
  
  /**
   * get recipient of the IAccountUpdate
   * @return
   */
  public Integer getTo(); 
  
  /**
   * get sender of the IAccountUpdate, if applicable
   * @return
   */
  public Integer getFrom(); 
  
  /**
   * get money to be exchanged in the IAccountUpdate
   * @return
   */
  public Double getCost(); 
  
  /**
   * get ICart about to be exchanged in the IAccountUpdate
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
