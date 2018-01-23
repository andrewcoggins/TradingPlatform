package brown.rules.library;

import java.util.List;

import brown.bid.AbsBid;
import brown.bid.library.AuctionBid;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.TradeableType;

public class SSSPActivity implements IActivityRule{
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    Integer agent = aBid.AgentID;    
    List<TradeMessage> currBids = state.getBids();
    boolean acceptable = true;
    for (TradeMessage bid : currBids){
      if (bid.AgentID == agent){
        acceptable = false;
        break;
      }
    }
    
    AbsBid bids = aBid.Bundle.getBids();
    if (!(bids instanceof AuctionBid)){
      acceptable = false;
    } else {
      for (ITradeable t: ((AuctionBid) bids).bids.keySet()){
        if (t.getType() != TradeableType.Simple){
          acceptable = false;
        }
      }
    }        
    state.setAcceptable(acceptable);
  }

  @Override
  public void setReserves() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub    
  }

}
