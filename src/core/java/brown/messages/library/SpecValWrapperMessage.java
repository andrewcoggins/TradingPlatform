package brown.messages.library;

/**
 * Gives information for the spectrum auction value generator, which
 * is stored agent-side. 
 * this includes a seed, and index, and a number of bidders.
 * @author andrew
 *
 */
public class SpecValWrapperMessage extends PrivateInformationMessage {
  private Long seed;
  private Integer index; 
  private Integer nBidders;
  
  /**
   * void kryo. 
   */
  public SpecValWrapperMessage() {
    super(null);
    this.seed = null;
    this.index = null;
    this.nBidders = null;
  }
  
  /**
   * 
   * @param ID message ID
   * @param seed seed for the value generator
   * @param index 
   * @param nBidders number of bidders in the auction. 
   */
  public SpecValWrapperMessage(Integer ID, long seed, int index, int nBidders) {
    super(ID);
    this.seed = seed;
    this.index = index;
    this.nBidders = nBidders;
  }
  
  /**
   * getter for seed. 
   * @return a long that seeds the value generator.
   */
  public Long getSeed() {
    Long toSend = this.seed;
    this.seed = null;
    return toSend;
    
  }
  
  /**
   * getter for index.
   * @return index integer. 
   */
  public Integer getIndex(){
    Integer toSend = this.index;
    this.index = null;    
    return toSend;
  }
  
  /**
   * getter for number of bidders.
   * @return integer number of bidders in auction.
   */
  public Integer getnBidders() {
    Integer toSend = this.nBidders;
    this.nBidders = null;
    return toSend;    
  }
  
}
