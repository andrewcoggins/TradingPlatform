package brown.messages.library;

import java.util.Map;

import brown.tradeable.ITradeable;
import brown.tradeable.library.ComplexTradeable;


public class SpecValValuationMessage extends PrivateInformationMessage {

  private Map<ComplexTradeable, Double> valuation;
  
  /**
   * Empty constructor for Kryo
   * DO NOT USE
   */
  public SpecValValuationMessage() {
    super(null);
    this.valuation = null;
  }
  
  public SpecValValuationMessage(Integer ID, Map<ComplexTradeable, Double> val) {
    super(ID);
    this.valuation =val;
  }
  
  public Map<ComplexTradeable, Double> getValuation() {
    return this.valuation; 
  }

}