package brown.messages.library;


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
  
  public PredictionMarketValuationMessage(Integer ID, Boolean coin, Integer num_decoys) {
    super(ID);
    this.coin = coin;
    this.num_decoys = num_decoys;
  }
  
  public Boolean getCoin() {
    return this.coin; 
  }
  
  public Integer getNumberDecoys() {
    return this.num_decoys; 
  }
}