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
