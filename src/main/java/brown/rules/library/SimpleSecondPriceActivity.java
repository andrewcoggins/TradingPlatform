package brown.rules.library;

import java.util.List;

import brown.bid.IBid;
import brown.bid.library.AuctionBid;
import brown.logging.Logging;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;
import brown.tradeable.ITradeable;
import brown.tradeable.library.TradeableType;

public class SimpleSecondPriceActivity implements IActivityRule {
  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    Integer agent = aBid.AgentID;    
    List<TradeMessage> currBids = state.getBids();
    boolean acceptable = true;
    for (TradeMessage bid : currBids){
      Logging.log("SSSP Activity:Curr Bid ID - " + aBid.AgentID + ", Resting bids ID - " + bid.AgentID);
      if (bid.AgentID == agent){
        acceptable = false;
        break;
      }
    }
    
    IBid bids = aBid.Bundle.getBids();
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
