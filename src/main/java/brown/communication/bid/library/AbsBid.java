package brown.communication.bid.library;

import brown.communication.bid.IBid;

public abstract class AbsBid implements IBid {

  private BidType type; 
  
  public AbsBid(BidType type) {
    this.type = type; 
  }
  
  public BidType getType() {
    return this.type; 
  }
  
}
