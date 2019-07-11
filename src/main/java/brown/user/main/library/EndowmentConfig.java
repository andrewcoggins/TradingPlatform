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

  @Override
  public String toString() {
    return "EndowmentConfig [distribution=" + distribution + ", generators="
        + generators + ", itemNames=" + itemNames + "]";
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((distribution == null) ? 0 : distribution.hashCode());
    result =
        prime * result + ((generators == null) ? 0 : generators.hashCode());
    result = prime * result + ((itemNames == null) ? 0 : itemNames.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    EndowmentConfig other = (EndowmentConfig) obj;
    if (distribution == null) {
      if (other.distribution != null)
        return false;
    } else if (!distribution.equals(other.distribution))
      return false;
    if (generators == null) {
      if (other.generators != null)
        return false;
    } else if (!generators.equals(other.generators))
      return false;
    if (itemNames == null) {
      if (other.itemNames != null)
        return false;
    } else if (!itemNames.equals(other.itemNames))
      return false;
    return true;
  }

}
