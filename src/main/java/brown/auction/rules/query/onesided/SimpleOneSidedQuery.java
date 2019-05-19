package brown.auction.rules.query.onesided;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IQueryRule;
import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.item.ICart;

public class SimpleOneSidedQuery extends AbsRule implements IQueryRule {

  @Override
  public void makeTradeRequest(IMarketState state, ICart items,
      List<ITradeMessage> bids, Integer agentID) {

    // TODO: somehow integrate some inner information into this.
    // how about... we add the market public state (assumed to be for the agent)
    // here.

    state.setTRequest(
        new TradeRequestMessage(0, agentID, BidType.OneSidedBidBundle, items));
  }

}
