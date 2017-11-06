package brown.rules.activityrules;

import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;


public interface ActivityRule {
  
  //this doesn't quite make sense. But maybe it can be morphed to work.
  //how to factor in a specific bid? Just take it into the internal state.
  //when we have a rule that takes in a query and asks if it is okay, may want an I/O.
  public void isAcceptable(MarketInternalState state, Bid aBid);
  
}
