package brown.rules.library;

import java.util.List;

import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;

public class OneShotActivity implements IActivityRule {

  @Override
  // Checks if agent has already bid
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    Integer agent = aBid.AgentID;    
    List<TradeMessage> currBids = state.getBids();
    boolean acceptable = true;
    for (TradeMessage bid : currBids){
      if (bid.AgentID == agent){
        acceptable = false;
        break;
      }
    }
    state.setAcceptable(acceptable);
  }


  @Override
  // Implement Later, not relevant to lemonade game
  public void setReserves() {
  }


  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }
}