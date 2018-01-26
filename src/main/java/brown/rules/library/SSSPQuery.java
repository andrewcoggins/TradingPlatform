package brown.rules.library;

import brown.channels.agent.library.SSSPChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class SSSPQuery implements IQueryRule {

	@Override
	public void makeChannel(IMarketState state) {	  
			state.setTRequest(new TradeRequestMessage(0, new SSSPChannel(state.getID())));
	}

  @Override
  public void reset() {    
  }	
}
