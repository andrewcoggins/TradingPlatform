package brown.rules.library;

import brown.accounting.library.Ledger;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.channels.MechanismType;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.IQueryRule;

public class SealedBidQuery implements IQueryRule {

	@Override
	public void makeChannel(ICompleteState state, Ledger ledger) {
		//if (state.getAllocation().getType().equals(BundleType.Simple)) {

			state.setTRequest(new TradeRequestMessage(0, 
					new SimpleAgentChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid, 
							(AuctionBidBundle) state.getbundleReserve(), state.getEligibility()), 
							MechanismType.SealedBid));
		//}
	}

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }	
}
