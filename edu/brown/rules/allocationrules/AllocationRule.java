package brown.rules.allocationrules;

import java.util.Map;
import java.util.Set;

import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.channels.MechanismType;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.tradeables.Asset;

/**
 * an allocation rule decides two things: 
 * @author andrew
 *
 */
public interface AllocationRule {

  
	public void tick(MarketInternalState state);
	
  public void setAllocation(MarketInternalState state);
  
  public void setBidRequest(MarketInternalState state);

	public void isPrivate(MarketInternalState state);
	
	public void isOver(MarketInternalState state);
	
  public void setBundleType(MarketInternalState state);

	public void withReserve(MarketInternalState state);

	void isValid(MarketInternalState state);

	void getAllocationType(MarketInternalState state);

  void setReport(MarketInternalState state);

}
