package brown.user.main.library;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import brown.user.main.IEndowmentConfig;

public class EndowmentConfig implements IEndowmentConfig {
  
  private Constructor<?> distribution; 
  private List<Constructor<?>> endowmentCons;
  private List<List<Double>> endowmentParams; 
  private List<String> itemNames; 
  
  public EndowmentConfig(List<String> itemNames, Constructor<?> distCons, 
      List<Constructor<?>> endowmentCons, List<List<Double>> endowmentParams) {
    this.itemNames = itemNames; 
    this.endowmentCons = endowmentCons; 
    this.endowmentParams = endowmentParams; 
    this.distribution = distCons;
  }
  
  public Constructor<?> getDistribution() {
    return this.distribution; 
  }
  
  public List<Constructor<?>> getGeneratorConstructors() {
    return this.endowmentCons;
  }
  
  public List<List<Double>> getGeneratorParams() {
    return this.endowmentParams; 
  }
  
  public List<String> getItemNames() {
    return this.itemNames; 
  }



}
