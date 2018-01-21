package brown.messages.library; 

import java.util.List;

import brown.messages.library.RegistrationMessage;
import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

/**
 * sends agents their valuations from the server
 * 
 * @author acoggins
 */
public class ValuationRegistrationMessage extends RegistrationMessage {
  
  private final IValuation personalValue; 
  private final IValuationDistribution allValuations;
  private final List<ITradeable> allGoods;
  
  public ValuationRegistrationMessage() {
    super(null);
    this.personalValue = null;
    this.allValuations = null;
    this.allGoods = null;
  }
  
  /**
   * valuation registration without underlying distribution.
   * @param id
   * @param toSend
   */
  public ValuationRegistrationMessage(Integer id, IValuation toSend, List<ITradeable> allGoods){ 
    super(id); 
    this.personalValue = toSend; 
    this.allValuations = null; 
    this.allGoods = allGoods;
  }
      
  /**
   * valuation registration with underlying distribution.
   * @param id
   * @param personalValue
   */
  public ValuationRegistrationMessage(Integer id, IValuation personalValue,
      IValuationDistribution distribution, List<ITradeable> allGoods) {
    super(id); 
    this.personalValue = personalValue; 
    this.allValuations = distribution;
    this.allGoods = allGoods;
  }
  
  /**
   * @return the agent's valuation
   */
  public IValuation getValuation() {
    return this.personalValue; 
  }
  
  public List<ITradeable> getGoods(){
    return this.allGoods;
  }
  
  /**
   * @return the distribution on which the agent is bidding, 
   * if applicable. 
   */
  public IValuationDistribution getDistribution() {
    return this.allValuations;
  }
  
}