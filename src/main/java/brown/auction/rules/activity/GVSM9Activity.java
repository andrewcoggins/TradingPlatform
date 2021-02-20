package brown.auction.rules.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IActivityRule;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.GVSM9BidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class GVSM9Activity extends AbsActivity implements IActivityRule {
	private static final int NATL = 0, REG1 = 1, REG2 = 2, REG3 = 3;

	@Override
	public void isAcceptable(IMarketState state, ITradeMessage aBid,
			List<ITradeMessage> currentBids, ICart items) {

		for (ITradeMessage msg : currentBids ) {
			if (msg.getAgentID().equals(aBid.getAgentID())) {
				state.setAcceptable(false);
				return;
			}
		}

		GVSM9BidBundle bid = (GVSM9BidBundle)aBid.getBid();

		if (bid.agentNum() != NATL && bid.getBids().size() > 3) {
			state.setAcceptable(false);
			return;
		}

		if (bid.getBids().size() > 6) {
			state.setAcceptable(false);
			return;
		}
		
		for (ICart cart : bid.getBids().keySet()) {
			if (cart.getItems().size() != 1) {
				state.setAcceptable(false);
				return;
			}
			
			IItem item = cart.getItems().get(0);
			String name = item.getName();
			if (bid.agentNum() == NATL &&
					(name.equals("G") || name.equals("H") || name.equals("I"))) {
				state.setAcceptable(false);
				return;
			}
			if (bid.agentNum() == REG1 &&
					(name.equals("E") || name.equals("F") || name.equals("H") || name.equals("I"))) {
				state.setAcceptable(false);
				return;
			}
			if (bid.agentNum() == REG2 &&
					(name.equals("A") || name.equals("B") || name.equals("G") || name.equals("I"))) {
				state.setAcceptable(false);
				return;
			}
			if (bid.agentNum() == REG3 &&
					(name.equals("C") || name.equals("D") || name.equals("G") || name.equals("H"))) {
				state.setAcceptable(false);
				return;
			}
		}

		for (Double x : bid.getBids().values()) {
			if (x < 0) {
				state.setAcceptable(false);
				return;
			}
		}

		state.setAcceptable(true);
		return;
	}

	@Override
	public void setReserves(IMarketState state, ICart items) {
		// noop
	}

}
