package brown.platform.messages.library;

import java.util.Map;

import brown.mechanism.tradeable.library.ComplexTradeable;

/**
 * Private Valuation message for spectrum auction.
 * @author kerry
 *
 */
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
  
  /**
   * Initialized with a message ID, and a map from complex tradeables (bundles) to their
   * values
   * @param ID message ID
   * @param val 
   */
  public SpecValValuationMessage(Integer ID, Map<ComplexTradeable, Double> val) {
    super(ID);
    this.valuation =val;
  }
  
  /**
   * simply returns the valuation.
   * @return a map from bundles of goods to their valuations.
   */
  public Map<ComplexTradeable, Double> getValuation() {
    return this.valuation; 
  }

}