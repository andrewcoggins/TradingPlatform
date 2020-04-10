package brown.auction.rules.allocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;
import brown.platform.item.library.Cart;

public class LastBidderMultiItem extends AbsRule implements IAllocationRule {

	// sets the allocation to the 'last bid standing' in an ascending auction.
	// if there are multiple last bids standing, chooses one at random.

	@Override
	public void setAllocation(IMarketState state, List<ITradeMessage> messages) {

		Map<Integer, List<ICart>> allocation = new HashMap<Integer, List<ICart>>();

		// get the most recent history.
		List<List<ITradeMessage>> tradeHistory = state.getTradeHistory();
		System.out.println(tradeHistory);
		if (this.tradeHistoryEmpty(tradeHistory)) {
			return;
		}

		Map<IItem, List<Integer>> bidders = new HashMap<>();
		Map<IItem, Integer> rounds = new HashMap<>();
		for (int round = 0; round < tradeHistory.size(); round++) {
			// copy + shuffle
			List<ITradeMessage> bids = new ArrayList<>(tradeHistory.get(round));
			Collections.shuffle(bids);
			for (ITradeMessage message : bids) {
				for (Map.Entry<ICart, Double> ent : message.getBid().getBids().entrySet()) {
					for (IItem item : ent.getKey().getItems()) {
						if (rounds.getOrDefault(item, -1) < round) {
							rounds.put(item, round);
							bidders.put(item, new ArrayList<>());
						}
						bidders.get(item).add(message.getAgentID());
					}
				}
			}
		}
		
		for (Map.Entry<IItem, List<Integer>> ent : bidders.entrySet()) {
			List<Integer> winners = ent.getValue();
			Collections.shuffle(winners);
			int winner = winners.get(0);
			
			if (!allocation.containsKey(winner)) {
				allocation.put(winner, new LinkedList<>());
				allocation.get(winner).add(new Cart());
			}
			allocation.get(winner).get(0).addToCart(ent.getKey());;
		}

		state.setAllocation(allocation);
	}

	private boolean tradeHistoryEmpty(List<List<ITradeMessage>> tradeHistory) {
		boolean allEmpty = true;
		if (tradeHistory.isEmpty())
			return true;
		for (List<ITradeMessage> tradeMessageList : tradeHistory) {
			if (!tradeMessageList.isEmpty()) {
				allEmpty = false;
				break;
			}
		}
		return allEmpty;
	}

}
