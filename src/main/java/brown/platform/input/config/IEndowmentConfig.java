package brown.platform.input.config;

import java.util.List;
import java.util.Map;

/**
 * Config for specifying endowment. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IEndowmentConfig extends IInputConfig {
  
  /**
   * get name
   * @return
   */
  public  String getName(); 
  
  /**
   * get endowment mapping. 
   * @return
   */
  public  Map<String, Integer> getEndowmentMapping(); 
  
  /**
   * get include mapping. 
   * @return
   */
  public  Map<String, List<String>> getIncludeMapping(); 
  
  /**
   * get money. 
   * @return
   */
  public  Double getMoney();
  
  /**
   * get frequency. 
   * @return
   */
  public  Integer getFrequency();
}