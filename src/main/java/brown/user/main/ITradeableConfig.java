package brown.user.main;

import java.lang.reflect.Constructor;

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
  public Constructor<?> getTType(); 
  
  /**
   * get number of tradeables. 
   * @return
   */
  public Integer getNumTradeables();
  
  
}