package brown.auction.rules.termination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.ITerminationCondition;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class ClockAuctionTermination extends AbsRule implements ITerminationCondition {

	@Override
	public void checkTerminated(IMarketState state, List<ITradeMessage> messages) {
		if (messages.isEmpty()) {
			state.close();
			return;
		}
		
		Map<String, Set<Integer>> demands = new HashMap<>();
		for (ITradeMessage msg : messages) {
			for (ICart cart : msg.getBid().getBids().keySet()) {
				for (IItem item : cart.getItems()) {
					demands.putIfAbsent(item.getName(), new HashSet<>());
					demands.get(item.getName()).add(msg.getAgentID());
				}
			}
		}
		
		
		if (demands.isEmpty()) {
			state.close();
			return;
		}
		
		boolean overDemand = false;
		for (String item : demands.keySet()) {
			if (demands.getOrDefault(item, new HashSet<>()).size() > 1) {
				overDemand = true;
			}
		}
		
		if (!overDemand) {
			state.close();
			return;
		}
	}
	
}
