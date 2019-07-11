package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * Config for specifying endowment. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IEndowmentConfig extends IInputConfig {
  
  public Constructor<?> getDistribution(); 
  
  public Map<Constructor<?>, List<Double>> getGenerators(); 
  
  public List<String> getItemNames(); 
  
}