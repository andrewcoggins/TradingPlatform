package brown.rules.queryrules.library;

import brown.accounting.BundleType;
import brown.accounting.Ledger;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.LemonadeChannel;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.market.marketstate.IMarketState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.queryrules.IQueryRule;
import brown.todeprecate.PaymentType;

public class LemonadeQueryOld implements IQueryRule {

  @Override
  public void makeChannel(IMarketState state, Ledger ledger) {
    // TODO Auto-generated method stub
    state.setTRequest(new TradeRequestMessage(0, 
        new SimpleAgentChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid, 
            (SimpleBidBundle) state.getbundleReserve(), state.getEligibility()), 
            MechanismType.SealedBid));
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