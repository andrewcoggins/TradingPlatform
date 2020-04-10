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
		if (state.getTicks() == 0) {
			return;
		}
		
		if (messages.isEmpty()) {
			System.out.println("NO MESSAGES!");
			state.close();
			return;
		}
	}
	
}
