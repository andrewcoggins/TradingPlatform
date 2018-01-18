package brown.rules.activityrules.library;

import java.util.List;

import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeMessage;
import brown.rules.activityrules.IActivityRule;

public class OneShotActivity implements IActivityRule {

  @Override
  // Checks if agent has already bid
  public void isAcceptable(ICompleteState state, TradeMessage aBid) {
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
}