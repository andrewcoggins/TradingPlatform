package brown.rules.paymentrules;

import java.util.List;
import java.util.Map;
import java.util.Set;

import brown.assets.accounting.Order;
import brown.bundles.BidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.tradeables.Asset;

public interface PaymentRule {

	public List<Order> getPayments(MarketInternalState state);
	
	public Map<BidBundle, Set<Asset>> getPayments(Map<Integer, Set<Asset>> allocations, Set<Bid> bids);

	public PaymentType getPaymentType();

	public BidBundle getReserve();
	
	public boolean permitShort();


}
