package brown.auction.rules.activity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class ClockAuctionActivity extends AbsActivity implements IActivityRule {
	private static final double EPSILON = 1.0;

	@Override
	public void isAcceptable(IMarketState state, ITradeMessage aBid, List<ITradeMessage> currentBids, ICart items) {

		// check that the submitted bid is greater than or equal to the reserve for
		// that agent (they should all be the same here).

		Map<String, Double> reserves = state.getReserves();

		if (!isWellFormed(aBid, items)) {
			state.setAcceptable(false);
		} else {
			IBidBundle bundle = (IBidBundle) aBid.getBid();
			Map<ICart, Double> carts = bundle.getBids();
			state.setAcceptable(true);
			for (ICart cart : carts.keySet()) {
				// all items are size 1
				if (cart.getItems().get(0).getItemCount() != 1) {
					state.setAcceptable(false);
					break;
				}
				// main condition: if the bid is too low for the reserve, reject.
				String itemName = cart.getItems().get(0).getName();
				if (reserves.containsKey(itemName)) {
					if (carts.get(cart) < reserves.get(itemName)) {
						state.setAcceptable(false);
					}
				} else {
					state.setAcceptable(false);
				}
			}
		}
	}

	@Override
	public void setReserves(IMarketState state, ICart items) {
		Map<String, Set<Integer>> demands = new HashMap<>();
		List<List<ITradeMessage>> history = state.getTradeHistory();
		for (ITradeMessage msg : history.get(history.size() - 1)) {
			for (ICart cart : msg.getBid().getBids().keySet()) {
				for (IItem item : cart.getItems()) {
					demands.putIfAbsent(item.getName(), new HashSet<>());
					demands.get(item.getName()).add(msg.getAgentID());
				}
			}
		}
		
		Map<String, Double> reserves = state.getReserves();
		for (String item : reserves.keySet()) {
			if (demands.getOrDefault(item, new HashSet<>()).size() > 1) {
				reserves.put(item, reserves.get(item) + EPSILON);
			}
		}
		state.setReserves(reserves);
	}

}
