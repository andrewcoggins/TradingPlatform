package brown.platform.input.config;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

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
  
  /**
   * get valuation distribution. 
   * @return
   */
  public Constructor<?> getValDistribution(); 
  
  
  /**
   * get generator config. 
   * @return
   */
  public Map<Constructor<?>, List<Double>> getGenerator(); 
  
}