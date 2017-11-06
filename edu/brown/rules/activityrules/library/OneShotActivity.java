package brown.rules.activityrules.library;

import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.rules.activityrules.ActivityRule;

public class OneShotActivity implements ActivityRule {

  @Override
  public void isAcceptable(MarketInternalState state, Bid aBid) {
    // TODO Auto-generated method stub
    boolean acceptable = !(state.getBids().contains(aBid));
    state.setAcceptable(acceptable);
  }
}