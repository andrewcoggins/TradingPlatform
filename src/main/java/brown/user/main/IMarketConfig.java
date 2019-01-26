package brown.user.main;

import java.util.List;
import java.util.Map;

import brown.platform.market.IMarketRules;

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
  public  IMarketRules getRules(); 
  
  /**
   * get num tradeables map. 
   * @return
   */
  public  Map<String, Integer> getNumTradeablesMap(); 
  
  /**
   * get mustInclude information. 
   * @return
   */
  public  Map<String, List<String>> getMustInclude();
}