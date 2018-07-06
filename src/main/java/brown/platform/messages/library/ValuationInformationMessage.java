package brown.platform.messages.library;

import java.util.List;

import brown.auction.value.distribution.IValuationDistribution;
import brown.auction.value.valuation.IValuation;
import brown.mechanism.tradeable.ITradeable;

/**
 * Valuation information message gives a typical valuation for a series
 * of tradeables. It may include a private valuation over goods as well as 
 * the distribution from which the agents' valuations are drawn, so the agent
 * can perform monte carlo sampling. 
 * @author andrew
 *
 */
public class ValuationInformationMessage extends PrivateInformationMessage {

  private List<ITradeable> allTradeables; 
  private IValuation privateValuation;
  private IValuationDistribution allValuations; 
  
  /**
   * Empty constructor for Kryo
   * DO NOT USE
   */
  public ValuationInformationMessage() {
    super(null);
    this.allTradeables = null;
    this.privateValuation = null;
    this.allValuations = null;     
  }
  
  /**
   * A ValuationInformationMessage specifies tradeables and valuation. 
   * @param ID message ID
   * @param allTradeables all tradeables that are valued.
   * @param privateValuation the private valuation over tradeables.
   */
  public ValuationInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
    this.allValuations = null;
  }

  /**
   * A ValuationInformationMessage specifies tradeables and valuation, and distribution. 
   * @param ID message ID
   * @param allTradeables all tradeables that are valued.
   * @param privateValuation the private valuation over tradeables.
   * @param allValuations the distribution from which agents' valuations are drawn.
   */
  public ValuationInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation, 
      IValuationDistribution allValuations) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
    this.allValuations = allValuations;
  }
  
  /**
   * getter for tradeables
   * @return all valued tradeables. 
   */
  public List<ITradeable> getTradeables() {
    return this.allTradeables; 
  }
  
  /**
   * getter for valuation
   * @return valuation over tradeables
   */
  public IValuation getPrivateValuation() {
    return this.privateValuation; 
  }
  
  /**
   * getter for valuation distribution
   * @return distribution from which valuations are drawn.
   */
  public IValuationDistribution getAllValuations() {
    return this.allValuations; 
  }
}