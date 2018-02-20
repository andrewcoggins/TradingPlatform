package brown.messages.library;

public class PredictionMarketInfoMessage extends PrivateInformationMessage {

  private Boolean coin;
  private Integer numDecoys;
  
  /**
   * Empty constructor for Kryo
   * DO NOT USE
   */
  public PredictionMarketInfoMessage() {
    super(null);
    this.coin = null;
    this.numDecoys = null;
  }
  
  public PredictionMarketInfoMessage(Integer ID, Boolean coin, Integer numDecoys) {
    super(ID);
    this.coin = coin;
    this.numDecoys = numDecoys;
  }

  
  public Boolean getCoin() {
    return this.coin;
  }
  
  public Integer getNumDecoys(){
    return this.numDecoys;
  }  
}