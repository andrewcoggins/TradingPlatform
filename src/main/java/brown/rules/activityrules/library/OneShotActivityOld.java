package brown.rules.activityrules.library;

import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.activityrules.IActivityRule;

/**
 * One shot activity rule. Dictates that an agent may only 
 * place one bid. 
 * @author acoggins
 *
 */
public class OneShotActivityOld implements IActivityRule {

  @Override
  public void isAcceptable(ICompleteState state, TradeMessage aBid) {
    // TODO Auto-generated method stub
    
  }

//	@Override
//	public boolean isAcceptable(MarketInternalState state, Bid bid) {
//		return !(state.getBids().contains(bid));
//	}

}
