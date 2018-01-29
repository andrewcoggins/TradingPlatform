package brown.rules.library;

import brown.channels.library.AuctionChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class SimpleSimultaneousQuery implements IQueryRule {

	@Override
	public void makeChannel(IMarketState state) {	  
			state.setTRequest(new TradeRequestMessage(0, new AuctionChannel(state.getID())));
	}

  @Override
  public void reset() {    
  }	
}
