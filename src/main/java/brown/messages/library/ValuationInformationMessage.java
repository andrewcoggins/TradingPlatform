package brown.messages.library;

import java.util.List;

import brown.tradeable.ITradeable;
import brown.value.distribution.IValuationDistribution;
import brown.value.valuation.IValuation;

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
  
  public ValuationInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
    this.allValuations = null;
  }

  public ValuationInformationMessage(Integer ID, List<ITradeable> allTradeables, IValuation privateValuation, 
      IValuationDistribution allValuations) {
    super(ID);
    this.allTradeables = allTradeables;
    this.privateValuation = privateValuation; 
    this.allValuations = allValuations;
  }
  
  public List<ITradeable> getTradeables() {
    return this.allTradeables; 
  }
  
  public IValuation getPrivateValuation() {
    return this.privateValuation; 
  }
  
  public IValuationDistribution getAllValuations() {
    return this.allValuations; 
  }
}