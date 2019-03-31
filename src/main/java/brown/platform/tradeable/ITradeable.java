package brown.platform.tradeable; 


import brown.platform.tradeable.library.TradeableType;

/**
 * Interface for items that are bid on and won in auctions.
 * @author acoggins
 *
 */
public interface ITradeable {
  
  /**
   * gets the ID of a tradeable.
   * @return inter ID
   */
  public Integer getID();
  
  /**
   * gets the count of a tradeable.
   * @return how many atomic tradeables are 
   * stored in this ITradeable
   */
  public String getName();
  
  /**
   * gets the type of a tradeable
   * @return a Tradeabletype enum
   */
  public TradeableType getType();
  
}