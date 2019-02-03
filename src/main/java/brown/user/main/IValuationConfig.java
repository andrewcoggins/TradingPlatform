package brown.user.main;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

public interface IValuationConfig {

  public Constructor<?> getValDistribution(); 
  
  public Map<Constructor<?>, List<Double>> getGenerators(); 
  
  public List<String> getTradeableNames(); 
}
