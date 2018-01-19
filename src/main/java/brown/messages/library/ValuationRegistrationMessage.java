package brown.messages.library; 

import brown.messages.library.RegistrationMessage;
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
  
  public ValuationRegistrationMessage() {
    super(null);
    this.personalValue = null;
    this.allValuations = null;
    
  }
  
  /**
   * valuation registration without underlying distribution.
   * @param id
   * @param toSend
   */
  public ValuationRegistrationMessage(Integer id, IValuation toSend){ 
    super(id); 
    this.personalValue = toSend; 
    this.allValuations = null; 
  }
      
  /**
   * valuation registration with underlying distribution.
   * @param id
   * @param personalValue
   */
  public ValuationRegistrationMessage(Integer id, IValuation personalValue,
      IValuationDistribution distribution) {
    super(id); 
    this.personalValue = personalValue; 
    this.allValuations = distribution;
  }
  
  /**
   * @return the agent's valuation
   */
  public IValuation getValuation() {
    return this.personalValue; 
  }
  
  /**
   * @return the distribution on which the agent is bidding, 
   * if applicable. 
   */
  public IValuationDistribution getDistribution() {
    return this.allValuations;
  }
  
}