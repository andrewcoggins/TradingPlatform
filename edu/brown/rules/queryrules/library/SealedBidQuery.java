package brown.rules.queryrules.library;

import brown.assets.accounting.Ledger;
import brown.bundles.BundleType;
import brown.bundles.SimpleBidBundle;
import brown.channels.MechanismType;
import brown.channels.library.SimpleAuctionChannel;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.markets.TradeRequest;
import brown.rules.queryrules.QueryRule;

public class SealedBidQuery implements QueryRule {

	@Override
	public void makeChannel(MarketInternalState state, Ledger ledger) {
		if (state.getAllocation().getType().equals(BundleType.Simple)) {
			state.setTRequest(new TradeRequest(0, 
					new SimpleAuctionChannel(state.getID(), ledger, state.getPaymentType(), MechanismType.SealedBid, 
							(SimpleBidBundle) state.getReserve(), state.getEligibility()), 
							MechanismType.SealedBid));
		}
	}	
}
