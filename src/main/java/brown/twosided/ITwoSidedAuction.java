package brown.twosided;

import brown.channels.agent.IAgentChannel;
import brown.tradeable.library.Tradeable;

public interface ITwoSidedAuction extends IAgentChannel {	
  
	public Tradeable getTradeableType();
	
}
