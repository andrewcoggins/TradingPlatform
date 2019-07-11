package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * Config for specifying endowment. See implementation for details. 
 * @author andrewcoggins
 *
 */
public interface IEndowmentConfig extends IInputConfig {
  
  public Constructor<?> getDistribution(); 
  
  public List<Constructor<?>> getGeneratorConstructors(); 
  
  public List<List<Double>> getGeneratorParams(); 
  
  public List<String> getItemNames(); 
  
}