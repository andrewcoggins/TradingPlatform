package brown.rules.queryrules.library;

import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.accounting.library.Ledger;
import brown.channels.MechanismType;
import brown.channels.agent.library.SimpleAgentChannel;
import brown.market.marketstate.ICompleteState;
import brown.messages.library.TradeRequestMessage;
import brown.rules.queryrules.IQueryRule;

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
}
