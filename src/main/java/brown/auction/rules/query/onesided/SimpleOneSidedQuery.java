package brown.auction.rules.query.onesided;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IQueryRule;
import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.tradeable.ITradeable;

public class SimpleOneSidedQuery extends AbsRule implements IQueryRule {

  @Override
  public void makeTradeRequest(IMarketState state, Map<String, List<ITradeable>> tradeables,
      List<ITradeMessage> bids, Integer agentID) {
    
    // TODO: somehow integrate some inner information into this. 
    // how about... we add the market public state (assumed to be for the agent) here. 
    
    state.setTRequest(new TradeRequestMessage(0, agentID, BidType.OneSidedBidBundle,
        new LinkedList<String>(tradeables.keySet()))); 
  }

}
