package brown.auction.rules.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messages.library.TradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class SMRAActivity extends AbsActivity implements IActivityRule {
	private static final double EPSILON = 3.0;

	@Override
	public void isAcceptable(IMarketState state, ITradeMessage aBid, List<ITradeMessage> currentBids, ICart items) {
		// check that the submitted bid is greater than or equal to the reserve for
		// that agent (they should all be the same here).
		Map<String, Double> reserves = state.getReserves();

		IBidBundle bundle = aBid.getBid();
		if (bundle == null) {
			bundle = new OneSidedBidBundle();
		}
		
		Map<ICart, Double> carts = bundle.getBids();
		state.setAcceptable(true);
		for (ICart cart : carts.keySet()) {
			if (cart.getItems().size() != 1) {
				state.setAcceptable(false);
				return;
			}
			
			// all items are size 1 and bids are over the reserve.
			for (IItem item : cart.getItems()) {
				if (item.getItemCount() != 1) {
					state.setAcceptable(false);
					return;
				}
				
				if (reserves.containsKey(item.getName())) {
					if (carts.get(cart) < reserves.get(item.getName())) {
						state.setAcceptable(false);
						return;
					}
				} else {
					state.setAcceptable(false);
					return;
				}
			}
		}
	}

	@Override
	public void setReserves(IMarketState state, ICart items) {
		Map<String, Double> reserves = new HashMap<>();
		if (state.getReserves() != null) {
			state.getReserves().entrySet().forEach(ent -> reserves.put(ent.getKey(), ent.getValue()));
		}
		
		for (IItem item : items.getItems()) {
			if (!reserves.containsKey(item.getName())) {
				reserves.put(item.getName(), EPSILON);
			}
		}
		
		if (state.getTicks() == 0) {
			state.setReserves(reserves);
			return;
		}
		
		// for each good
		// if multiple bidders, set to second highest bid
		// else if the good changed hands, increment by epsilon
		
		// good -> agent -> bid
		Map<String, Map<Integer, Double>> demands = new HashMap<>();
		List<List<ITradeMessage>> history = state.getTradeHistory();
		for (ITradeMessage msg : history.get(history.size() - 1)) {
			if (msg.getAgentID().intValue() == -1) {
				continue;
			}
			for (Map.Entry<ICart, Double> ent : msg.getBid().getBids().entrySet()) {
				ICart cart = ent.getKey();
				Double bid = ent.getValue();
				for (IItem item : cart.getItems()) {
					demands.putIfAbsent(item.getName(), new HashMap<>());
					demands.get(item.getName()).put(msg.getAgentID(), bid);
				}
			}
		}
		
		
		for (String item : demands.keySet()) {
			if (demands.get(item).size() <= 1) {
				reserves.put(item, reserves.get(item) + EPSILON);
			} else {
				List<Double> bids = new ArrayList<>(demands.get(item).values());
				Collections.sort(bids);
				reserves.put(item, bids.get(bids.size() - 2));
			}
		}
		state.setReserves(reserves);
	}

}
