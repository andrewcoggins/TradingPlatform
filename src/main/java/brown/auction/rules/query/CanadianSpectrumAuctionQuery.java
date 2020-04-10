package brown.auction.rules.query;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IQueryRule;
import brown.auction.value.distribution.library.GSVM18ValuationDistribution;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.ITradeRequestMessage;
import brown.communication.messages.library.SATSTradeRequestMessage;
import brown.communication.messages.library.TradeRequestMessage;
import brown.platform.item.ICart;
import brown.platform.utils.Utils;

public class CanadianSpectrumAuctionQuery extends AbsRule implements IQueryRule {

	@Override
	public void makeTradeRequest(Integer marketID, IMarketState state, ICart items, List<ITradeMessage> bids,
			Integer agentID) {
		TradeRequestMessage message = new TradeRequestMessage(0, marketID, agentID, items);
//		message.addInformation(Utils.toPublicState(state));
//		System.out.println("HERE LIES JOIN\t" + message.getState());
		state.setTRequest(message);
	}

}
