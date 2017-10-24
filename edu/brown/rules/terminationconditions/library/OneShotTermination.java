package brown.rules.terminationconditions.library;

import brown.marketinternalstates.MarketInternalState;
import brown.rules.terminationconditions.TerminationCondition;


public class OneShotTermination implements TerminationCondition {

	@Override
	public boolean isOver(MarketInternalState state) {
		return state.getBids().size() > 0;
	}

}
