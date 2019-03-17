package brown.communication.bid;

import brown.communication.bid.library.BidDirection;

public interface ITwoSidedBidBundle extends IOneSidedBidBundle {

  public BidDirection getBidDirection(); 
  
}
