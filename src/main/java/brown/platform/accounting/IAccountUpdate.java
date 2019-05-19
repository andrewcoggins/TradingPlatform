package brown.platform.accounting;

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

}
