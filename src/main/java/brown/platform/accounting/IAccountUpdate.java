package brown.platform.accounting;

import brown.platform.item.ICart;

/**
 * Object to specify an update to an account resulting from an auction.
 * 
 * @author andrewcoggins
 *
 */
public interface IAccountUpdate {

  /**
   * Converts the IAccountUpdate to an ITransaction.
   * 
   * @return an ITransaction reflecting the IAccountUpdate
   */
  public ITransaction toTransaction();
  
  /**
   * Gets the recipient of the IAccountUpdate.
   * 
   * @return
   */
  public Integer getTo(); 
  
  /**
   * Gets the sender of the IAccountUpdate, if applicable.
   * 
   * @return
   */
  public Integer getFrom(); 
  
  /**
   * Gets the money to be exchanged in the IAccountUpdate.
   * 
   * @return
   */
  public Double getCost(); 
  
  /**
   * Gets the ICart to be exchanged in the IAccountUpdate.
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
