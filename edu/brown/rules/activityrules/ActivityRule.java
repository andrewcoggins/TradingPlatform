package brown.rules.activityrules;

import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;


public interface ActivityRule {

	//public boolean isAcceptable(MarketInternalState state, Bid bid);
  
  public void isAcceptable();
}
