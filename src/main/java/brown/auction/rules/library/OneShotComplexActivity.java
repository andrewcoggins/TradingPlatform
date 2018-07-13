package brown.auction.rules.library;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.mechanism.bid.IBid;
import brown.mechanism.bid.library.AuctionBid;
import brown.mechanism.tradeable.ITradeable;
import brown.mechanism.tradeable.library.TradeableType;
import brown.platform.messages.library.TradeMessage;

public class OneShotComplexActivity implements IActivityRule {
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    Integer agent = aBid.AgentID;    
    List<TradeMessage> currBids = state.getBids();
    boolean acceptable = true;
    for (TradeMessage bid : currBids){
      if (bid.AgentID == agent) {
        acceptable = false;
        break;
      }
    }
    
    IBid bids = aBid.Bundle.getBids();
    if (!(bids instanceof AuctionBid)) {
      acceptable = false;
    } else {
      for (ITradeable t: ((AuctionBid) bids).bids.keySet()) {
        // make sure the bids are complex.
        if (t.getType() != TradeableType.Complex) {
          acceptable = false;
        }
      }
    }        
    state.setAcceptable(acceptable);
  }

  @Override
  public void setReserves(IMarketState state) {
    // TODO Auto-generated method stub
    
  }

}