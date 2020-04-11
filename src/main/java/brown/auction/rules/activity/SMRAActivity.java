package brown.auction.rules.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.IActivityRule;
import brown.auction.value.valuation.library.GSVM18Valuation;
import brown.communication.bid.IBidBundle;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class SMRAActivity extends AbsActivity implements IActivityRule {
	public static final double EPSILON = 2.5;

	@Override
	public void isAcceptable(IMarketState state, ITradeMessage aBid, List<ITradeMessage> currentBids, ICart items) {
		// check that the submitted bid is greater than or equal to the reserve for
		// that agent (they should all be the same here).
		Map<String, Double> reserves = state.getReserves();
		
		int agent = aBid.getAgentID();

		IBidBundle bundle = aBid.getBid();
		if (bundle == null) {
			state.setAcceptable(false);
			return;
		}
		
		int numItems = 0;
		
		Map<ICart, Double> carts = bundle.getBids();
		state.setAcceptable(true);
		for (ICart cart : carts.keySet()) {
			if (cart.getItems().size() != 1) {
				state.setAcceptable(false);
				return;
			}
			
			// all items are size 1 and bids are over the reserve.
			for (IItem item : cart.getItems()) {
				numItems++;
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
		
		Integer pos = GSVM18Valuation.positionOf(agent);
		if (pos == null || pos.intValue() < 1 || pos.intValue() > 7 || (pos.intValue() != 7 && numItems > 4) || numItems > 12) {
			state.setAcceptable(false);
			return;
		}
		
		// revealed preference rule
		Set<String> newBundle = new HashSet<>();
		double currPriceNewBundle = 0.0;
		for (Map.Entry<ICart, Double> ent : carts.entrySet()) {
			for (IItem item : ent.getKey().getItems()) {
				newBundle.add(item.getName());
				currPriceNewBundle += state.getReserves().get(item.getName());
			}
		}
		
		List<List<ITradeMessage>> history = state.getTradeHistory();
		for (int round = 0; round < history.size(); round++) {
			// get this bidder's bid and the reserve
			Set<String> oldBundle = new HashSet<>();
			Map<String, Double> oldReserves = new HashMap<>();
			for (ITradeMessage message : history.get(round)) {
				if (message.getAgentID().intValue() == agent && message.getAuctionID().intValue() >= 0) {
					for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
						for (IItem item : ent.getKey().getItems()) {
							oldBundle.add(item.getName());
						}
					}
				} else if (message.getAgentID().intValue() == -1 && message.getAuctionID().intValue() == -1) {
					for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
						for (IItem item : ent.getKey().getItems()) {
							oldReserves.put(item.getName(), ent.getValue());
						}
					}
				}
			}
			if (oldReserves.isEmpty() || newBundle.equals(oldBundle)) {
				continue;
			}
			
			double currPriceOldBundle = 0.0;
			double oldPriceNewBundle = 0.0;
			double oldPriceOldBundle = 0.0;
			
			for (String good : newBundle) {
				oldPriceNewBundle += oldReserves.get(good);
			}
			
			for (String good : oldBundle) {
				oldPriceOldBundle += oldReserves.get(good);
				currPriceOldBundle += state.getReserves().get(good);
			}
			
			if ((currPriceOldBundle - oldPriceOldBundle) < (currPriceNewBundle - oldPriceNewBundle)) {
				state.setAcceptable(false);
				return;
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
		
		if (state.getTicks() == 0 || state.getTradeHistory().size() < 2) {
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
		
		// get the previous allocation
		Map<String, Integer> prevAlloc = new HashMap<>();
		for (ITradeMessage message : history.get(history.size() - 2)) {
			if (message.getAuctionID().intValue() != -2) {
				continue;
			}
			
			int agent = message.getAgentID().intValue();
			for (ICart cart : message.getBid().getBids().keySet()) {
				for (IItem item : cart.getItems()) {
					prevAlloc.put(item.getName(), agent);
				}
			}
		}
		
		Map<String, Integer> currAlloc = new HashMap<>();
		for (Map.Entry<Integer, List<ICart>> ent : state.getAllocation().entrySet()) {
			int agent = ent.getKey().intValue();
			for (ICart cart : ent.getValue()) {
				for (IItem item : cart.getItems()) {
					currAlloc.put(item.getName(), agent);
				}
			}
		}
		
		
		for (String item : demands.keySet()) {
			if (demands.get(item).size() <= 1) {
				Integer previousOwner = prevAlloc.getOrDefault(item, null);
				if (previousOwner == null || previousOwner.intValue() != currAlloc.get(item).intValue()) {
					reserves.put(item, reserves.get(item) + EPSILON);
				}
			} else {
				List<Double> bids = new ArrayList<>(demands.get(item).values());
				Collections.sort(bids);
				reserves.put(item, bids.get(bids.size() - 2) + EPSILON);
			}
		}
		state.setReserves(reserves);
	}

}
