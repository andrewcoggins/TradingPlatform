package brown.auction.rules.query;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IQueryRule;
import brown.auction.value.distribution.library.CanadianSpectrumValuationDistribution;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.SATSTradeRequestMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.item.ICart;

public class CanadianSpectrumAuctionQuery extends AbsRule implements IQueryRule {

	@Override
	public void makeTradeRequest(Integer marketID, IMarketState state, ICart items, List<ITradeMessage> bids,
			Integer agentID) {
		state.setTRequest(new SATSTradeRequestMessage(0, marketID, agentID, items,
				CanadianSpectrumValuationDistribution.agentIDToSATSBidder().get(agentID)));
	}

}
