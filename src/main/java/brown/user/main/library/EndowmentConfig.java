package brown.user.main.library;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import brown.user.main.IEndowmentConfig;

public class EndowmentConfig implements IEndowmentConfig {
  
  private Constructor<?> distribution; 
  private Map<Constructor<?>, List<Double>> generators; 
  private List<String> itemNames; 
  
  public EndowmentConfig(List<String> itemNames, Constructor<?> distCons, Map<Constructor<?>, List<Double>> generators) {
    this.itemNames = itemNames; 
    this.generators = generators; 
    this.distribution = distCons;
  }
  
  public Constructor<?> getDistribution() {
    return this.distribution; 
  }
  
  public Map<Constructor<?>, List<Double>> getGenerators() {
    return this.generators;
  }
  
  public List<String> getItemNames() {
    return this.itemNames; 
  }

  
}
