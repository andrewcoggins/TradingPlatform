package brown.rules.queryrules.library;

import brown.assets.accounting.Ledger;
import brown.channels.MechanismType;
import brown.channels.library.LemonadeChannel;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.markets.TradeRequest;
import brown.rules.queryrules.QueryRule;

public class LemonadeQuery implements QueryRule {

  @Override
  public void makeChannel(MarketInternalState state, Ledger ledger) {
    // TODO Auto-generated method stub
      TradeRequest constructedRequest = new TradeRequest(0, 
          new LemonadeChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid), 
              MechanismType.Lemonade);
      state.setTRequest(constructedRequest);
  }
 }
  
  
