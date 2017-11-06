package brown.rules.queryrules.library;

import brown.assets.accounting.Ledger;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.channels.library.LemonadeChannel;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.markets.TradeRequest;
import brown.rules.paymentrules.PaymentType;
import brown.rules.queryrules.QueryRule;

public class LemonadeQueryOld implements QueryRule {

  @Override
  public void makeChannel(MarketInternalState state, Ledger ledger) {
    // TODO Auto-generated method stub
    
  }
  
//  @Override
//  public void makeChannel(Ledger ledger, PaymentType type, MarketInternalState state) {
//    if (state.getAllocation().getType().equals(BundleType.Simple)) {
//      //
//      TradeRequest constructedRequest = new TradeRequest(0, 
//          new LemonadeChannel(state.getID(), ledger, type, MechanismType.SealedBid), 
//              MechanismType.Lemonade);
//      state.setChannel(constructedRequest);
//    }
//  
//}
}