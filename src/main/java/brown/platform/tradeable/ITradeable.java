package brown.platform.tradeable;

/**
 * Interface for items in auctions.
 * @author acoggins
 *
 */
public interface ITradeable {
  
  /**
   * gets the ID of a tradeable.
   * @return integer ID
   */
  public Integer getID();
  
  /**
   * gets the name of a tradeable.
   * @return string Name
   */
  public String getName();
  
}
