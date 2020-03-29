package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Config for specifying endowment. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IEndowmentConfig extends IInputConfig {
  
  /**
   * get endowment distribution
   * @return
   */
  public Constructor<?> getDistribution(); 
  
  /**
   * get constructors for distribution generators. 
   * @return
   */
  public List<Constructor<?>> getGeneratorConstructors(); 
  
  /** 
   * get generator parameters. 
   * @return
   */
  public List<List<Double>> getGeneratorParams(); 
  
  /**
   * get item names. 
   * @return
   */
  public List<String> getItemNames(); 
  
}