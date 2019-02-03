package brown.user.main;

import java.util.List;

/**
 * config for specifying user-given parameters for simulation. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface ISimulationConfig extends IInputConfig {
  
  /**
   * get number of simulation runs. 
   * @return
   */
  public  Integer getSimulationRuns();
  
  /**
   * get tradeable config. 
   * @return
   */
  public  List<ITradeableConfig> getTConfig(); 
   
  /**
   * get valuation config
   * @return
   */
  public List<IValuationConfig> getVConfig(); 
  
  /**
   * get endowment config. 
   * @return
   */
  public  List<IEndowmentConfig> getEConfig(); 
  
  /**
   * get market config. 
   * @return
   */
  public  List<List<IMarketConfig>> getMConfig();
}