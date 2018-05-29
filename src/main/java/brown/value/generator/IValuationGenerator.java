package brown.value.generator; 

/**
 * IValuationGenerator produces doubles according to a specified distribution.
 * @author acoggins
 *
 */
public interface IValuationGenerator {
  
  /**
   * makes a valuation according to some specifed distribution.
   * @return a valuation, represented as a double. 
   */
  public abstract Double makeValuation();
  
}