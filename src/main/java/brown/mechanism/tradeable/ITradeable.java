package brown.mechanism.tradeable; 

import java.util.List;

import brown.mechanism.tradeable.library.SimpleTradeable;
import brown.mechanism.tradeable.library.TradeableType;

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
  public Integer getCount();
  
  /**
   * gets the type of a tradeable
   * @return a Tradeabletype enum
   */
  public TradeableType getType();
  
  /**
   * flattens the ITradeable into a list of 
   * simple, atomic tradeables.=
   * @return a list of simple tradeables.
   */
  public List<SimpleTradeable> flatten();
}