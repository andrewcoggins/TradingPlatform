package brown.platform.tradeable; 

import java.util.List;

import brown.platform.tradeable.library.SimpleTradeable;
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
   * //TODO: make private. remove form interface.
   */
  public List<SimpleTradeable> flatten();
}