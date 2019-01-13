package brown.platform.input.config;

import brown.auction.value.distribution.IValuationDistribution;
import brown.mechanism.tradeable.library.TradeableType;

/**
 * config for specifying tradeables from user-given input. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface ITradeableConfig extends IInputConfig {
  
  /**
   * get the moniker for the tradeables. 
   * @return
   */
  public String getTradeableName(); 
  
  /**
   * get tradeable type. 
   * @return
   */
  public TradeableType getTType(); 
  
  /**
   * get number of tradeables. 
   * @return
   */
  public Integer getNumTradeables();
  
  /**
   * get valuation distribution. 
   * @return
   */
  public IValuationDistribution getValDistribution(); 
  
}