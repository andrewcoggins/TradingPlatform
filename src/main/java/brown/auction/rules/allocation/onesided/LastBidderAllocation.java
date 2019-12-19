package brown.auction.rules.allocation.onesided;

import java.util.List;

import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IAllocationRule;
import brown.communication.messages.ITradeMessage;

public class LastBidderAllocation extends AbsRule implements IAllocationRule {

  // so, this will need a complete inner history. 
  // intstead of providing one marketstate, why not provide a list of marketstate? 
  // solution: add a trade history. 
  @Override
  public void setAllocation(IMarketState state, List<ITradeMessage> messages) {
    // TODO Auto-generated method stub
    
  }

}
