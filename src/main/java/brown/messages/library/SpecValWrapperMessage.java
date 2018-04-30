package brown.messages.library;

public class SpecValWrapperMessage extends PrivateInformationMessage {
  private Long seed;
  private Integer index; 
  private Integer nBidders;
  
  public SpecValWrapperMessage() {
    super(null);
    this.seed = null;
    this.index = null;
    this.nBidders = null;
  }
  
  public SpecValWrapperMessage(Integer ID, long seed, int index, int nBidders) {
    super(ID);
    this.seed = seed;
    this.index = index;
    this.nBidders = nBidders;
  }
  
  public Long getSeed(){
    Long toSend = this.seed;
    this.seed = null;
    return toSend;
    
  }
  
  public Integer getIndex(){
    Integer toSend = this.index;
    this.index = null;    
    return toSend;
  }
  
  public Integer getnBidders(){
    Integer toSend = this.nBidders;
    this.nBidders = null;
    return toSend;    
  }
  
}
