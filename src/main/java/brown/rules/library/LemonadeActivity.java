package brown.rules.library;

import java.util.List;

import brown.bidbundle.BundleType;
import brown.bidbundle.library.GameBidBundle;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeMessage;
import brown.rules.IActivityRule;

public class LemonadeActivity implements IActivityRule {
  private int numSlots;
  
  public LemonadeActivity(int numSlots) {
    this.numSlots = numSlots;
  }


  @Override
  public void isAcceptable(IMarketState state, TradeMessage aBid) {
    // Check that person has not already bid
    Integer agent = aBid.AgentID;    
    List<TradeMessage> currBids = state.getBids();
    boolean acceptable = true;
    for (TradeMessage bid : currBids){
      if (bid.AgentID == agent){
        acceptable = false;
        break;
      }
    }
    
    // Check that persons bid is a valid slot
    if (aBid.Bundle.getType() != BundleType.GAME){    
      acceptable = false;
    } else if (((GameBidBundle) aBid.Bundle).getBids().move >= numSlots | ((GameBidBundle) aBid.Bundle).getBids().move < 0){    
      acceptable = false;
    }
    
    state.setAcceptable(acceptable);
  }


  @Override
  // Implement Later, not relevant to lemonade game
  public void setReserves(IMarketState state) {
  }


  @Override
  public void reset() {
  }
}