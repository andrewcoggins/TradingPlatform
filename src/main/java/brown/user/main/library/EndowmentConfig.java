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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((distribution == null) ? 0 : distribution.hashCode());
    result = prime * result
        + ((endowmentCons == null) ? 0 : endowmentCons.hashCode());
    result = prime * result
        + ((endowmentParams == null) ? 0 : endowmentParams.hashCode());
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
    if (endowmentCons == null) {
      if (other.endowmentCons != null)
        return false;
    } else if (!endowmentCons.equals(other.endowmentCons))
      return false;
    if (endowmentParams == null) {
      if (other.endowmentParams != null)
        return false;
    } else if (!endowmentParams.equals(other.endowmentParams))
      return false;
    if (itemNames == null) {
      if (other.itemNames != null)
        return false;
    } else if (!itemNames.equals(other.itemNames))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "EndowmentConfig [distribution=" + distribution + ", endowmentCons="
        + endowmentCons + ", endowmentParams=" + endowmentParams
        + ", itemNames=" + itemNames + "]";
  }

}
