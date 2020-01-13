package brown.auction.rules.ir;

import java.util.List;

import brown.auction.marketstate.IMarketPublicState;
import brown.auction.marketstate.IMarketState;
import brown.auction.rules.AbsRule;
import brown.auction.rules.IInformationRevelationPolicy;
import brown.communication.messages.ITradeMessage;

public class NonAnonymousPolicy extends AbsRule implements IInformationRevelationPolicy {

  @Override
  public void updatePublicState(IMarketState state, IMarketPublicState publicState) {
    
   
    publicState.tick();
    List<ITradeMessage> recentHistory = state.getTradeHistory().get(state.getTradeHistory().size() - 1); 
    publicState.addToTradeHistory(recentHistory);
    
    publicState.setAllocation(state.getAllocation());
    publicState.setPayments(state.getPayments());
    
  }


}
