package brown.user.main;

import java.util.List;

import brown.platform.market.IFlexibleRules;

/**
 * config for specifying markets. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IMarketConfig extends IInputConfig {
  
  /**
   * get rules
   * @return
   */
  public  IFlexibleRules getRules(); 
  
  /**
   * get num tradeables map. 
   * @return
   */
  public List<String> getTradeableNames(); 
  
}