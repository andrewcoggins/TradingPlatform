package brown.user.main;

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
   * get number of tradeables. 
   * @return
   */
  public Integer getNumTradeables();
  
  
}