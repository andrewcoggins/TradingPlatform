package brown.user.main.library;

import java.lang.reflect.Constructor;
import java.util.List;

import brown.user.main.IValuationConfig;

public class ValuationConfig implements IValuationConfig {
  
  private Constructor<?> valDistribution; 
  private List<Constructor<?>> valuationCons;
  private List<List<Double>> valuationParams; 
  private List<String> itemNames; 
  
  public ValuationConfig(List<String> itemNames, Constructor<?> distCons, 
      List<Constructor<?>> valuationCons, List<List<Double>> valuationParams) {
    this.itemNames = itemNames; 
    this.valuationCons = valuationCons; 
    this.valuationParams = valuationParams; 
    this.valDistribution = distCons;
  }
  
  public Constructor<?> getValDistribution() {
    return this.valDistribution; 
  }
  
  public List<Constructor<?>> getGeneratorConstructors() {
    return this.valuationCons;
  }
  
  public List<List<Double>> getGeneratorParams() {
    return this.valuationParams; 
  }
  
  
  public List<String> getItemNames() {
    return this.itemNames; 
  }

}
