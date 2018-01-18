package brown.rules.queryrules.library;

import brown.accounting.library.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.library.LemonadeChannel;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.queryrules.IQueryRule;

public class LemonadeQuery implements IQueryRule {

  @Override
  public void makeChannel(ICompleteState state, Ledger ledger) {
    // TODO Auto-generated method stub
      TradeRequestMessage constructedRequest = new TradeRequestMessage(0, 
          new LemonadeChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid), 
              MechanismType.Lemonade);
      state.setTRequest(constructedRequest);
  }
  
}