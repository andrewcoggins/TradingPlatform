package brown.auction.rules.termination;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.distribution.ExponentialDistribution;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.ITerminationCondition;
import brown.communication.messages.ITradeMessage;
import brown.platform.item.ICart;
import brown.platform.item.IItem;

public class ClockAuctionTermination extends AbsRule implements ITerminationCondition {
	private static final double LAMBDA = 4.0;
	private static final int MIN_END_ROUND = 30;
	private static Integer END_ROUND = null;
	

	@Override
	public void checkTerminated(IMarketState state, List<ITradeMessage> messages) {
		if (END_ROUND == null) {
			reset();
		}
		
		if (state.getTicks() == 0) {
			return;
		}
		
		if (messages.isEmpty()) {
			state.close();
			return;
		}
		
		if (state.getTicks() > END_ROUND) {
			state.close();
			reset();
			return;
		}
	}
	
	private static void reset() {
		END_ROUND = new Double((Math.log(1 - Math.random()) / -LAMBDA) + MIN_END_ROUND).intValue();
	}
	
}
