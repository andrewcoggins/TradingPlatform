package brown.auction.value.generator.library;

import java.util.List;

import brown.auction.value.generator.IValuationGenerator;

public abstract class AbsValuationGenerator implements IValuationGenerator {
  
  protected List<Double> params; 
  
  public AbsValuationGenerator() {
    this.params = null; 
  }
  
  public AbsValuationGenerator(List<Double> params) {
    this.params = params;
  }
  
  @Override
  public abstract Double makeValuation();

  @Override
  public String toString() {
    return "AbsValuationGenerator [params=" + params + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((params == null) ? 0 : params.hashCode());
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
    AbsValuationGenerator other = (AbsValuationGenerator) obj;
    if (params == null) {
      if (other.params != null)
        return false;
    } else if (!params.equals(other.params))
      return false;
    return true;
  } 
  
  
}
