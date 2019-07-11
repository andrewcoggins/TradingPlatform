package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;

public interface IValuationConfig {

  public Constructor<?> getValDistribution(); 
  
  public List<Constructor<?>> getGeneratorConstructors(); 
  
  public List<List<Double>> getGeneratorParams(); 
  
  public List<String> getItemNames(); 
}
