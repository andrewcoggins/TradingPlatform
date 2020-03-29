package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * config for agent valuation. 
 * @author andrewcoggins
 *
 */
public interface IValuationConfig {

  /**
   * get valuation distribution. 
   * @return
   */
  public Constructor<?> getValDistribution(); 
  
  /**
   * get valuation generator constructors. 
   * @return
   */
  public List<Constructor<?>> getGeneratorConstructors(); 
  
  /**
   * get valuation generator parameters. 
   * @return
   */
  public List<List<Double>> getGeneratorParams(); 
  
  /**
   * get item names.
   * @return
   */
  public List<String> getItemNames(); 
}
