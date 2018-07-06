package brown.platform.messages.library;

/**
 * Private information message giving coin information in 
 * prediction market.
 * @author kerry
 *
 */
public class PredictionMarketValuationMessage extends PrivateInformationMessage {

  private Boolean coin;
  private Integer num_decoys;
  
  /**
   * Empty constructor for Kryo
   * DO NOT USE
   */
  public PredictionMarketValuationMessage() {
    super(null);
    this.coin=null;
    this.num_decoys=null;
  }
  
  /**
   * A Prediction market valuation message is described by a market(?) ID, 
   * an observed coin value, and a number of decoys. 
   * @param ID
   * @param coin
   * @param num_decoys
   */
  public PredictionMarketValuationMessage(Integer ID, Boolean coin, Integer num_decoys) {
    super(ID);
    this.coin = coin;
    this.num_decoys = num_decoys;
  }
  
  /**
   * Getter for coin
   * @return true if heads
   */
  public Boolean getCoin() {
    return this.coin; 
  }
  
  /**
   * Getter for number of decoys
   * @return number of decoy coins.
   */
  public Integer getNumberDecoys() {
    return this.num_decoys; 
  }
}