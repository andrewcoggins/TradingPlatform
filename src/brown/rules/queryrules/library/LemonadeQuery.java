package brown.rules.queryrules.library;

import brown.accounting.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.library.LemonadeChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequest;
import brown.rules.queryrules.IQueryRule;

public class LemonadeQuery implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state, Ledger ledger) {
    // TODO Auto-generated method stub
      TradeRequest constructedRequest = new TradeRequest(0, 
          new LemonadeChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid), 
              MechanismType.Lemonade);
      state.setTRequest(constructedRequest);
  }
 }
  
  
