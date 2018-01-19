package brown.rules.library;

import brown.accounting.library.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.library.LemonadeChannel;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class LemonadeQuery implements IQueryRule {

  @Override
  public void makeChannel(ICompleteState state, Ledger ledger) {
      TradeRequestMessage constructedRequest = new TradeRequestMessage(0, 
          new LemonadeChannel(state.getID(), ledger),MechanismType.Lemonade);
      state.setTRequest(constructedRequest);
  }

  @Override
  public void reset() {
  }  
}
