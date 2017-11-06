package brown.rules.activityrules.library;

import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.rules.activityrules.ActivityRule;

/**
 * One shot activity rule. Dictates that an agent may only 
 * place one bid. 
 * @author acoggins
 *
 */
public class OneShotActivityOld implements ActivityRule {

  @Override
  public void isAcceptable(MarketInternalState state, Bid aBid) {
    // TODO Auto-generated method stub
    
  }

//	@Override
//	public boolean isAcceptable(MarketInternalState state, Bid bid) {
//		return !(state.getBids().contains(bid));
//	}

}
