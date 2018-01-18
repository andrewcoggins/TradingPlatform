package brown.twosided;

import brown.channels.agent.IAgentChannel;
import brown.tradeable.library.MultiTradeable;

public interface ITwoSidedAuction extends IAgentChannel {	
  
	public MultiTradeable getTradeableType();
	
}
