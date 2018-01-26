package brown.rules.library;

import brown.channels.agent.library.LemonadeChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class LemonadeQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state) {
    state.setTRequest(new TradeRequestMessage(0,new LemonadeChannel(state.getID())));      
  }

  @Override
  public void reset() {
  }  
}
