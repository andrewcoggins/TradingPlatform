package brown.rules.allocationrules.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import brown.auctions.arules.MechanismType;
import brown.bundles.BidBundle;
import brown.bundles.BundleType;
import brown.bundles.MarketState;
import brown.bundles.SimpleBidBundle;
import brown.marketinternalstates.MarketInternalState;
import brown.messages.auctions.Bid;
import brown.messages.auctions.BidRequest;
import brown.messages.markets.GameReport;
import brown.rules.allocationrules.AllocationRule;
import brown.setup.Logging;
import brown.tradeables.Asset;
import brown.valuable.library.Tradeable;

/**
 * implements an allcation rule where the highest bidder is allocated the good(?)
 * @author andrew
 *
 */
public class SimpleHighestBidderAllocation implements AllocationRule {
  
	private final Map<Tradeable, MarketState> RESERVE;
	
	 /**
   * empty constructor for kryo- do not use
   */
  public SimpleHighestBidderAllocation() {
    this.RESERVE = new HashMap<Tradeable, MarketState>();
  }
  
  /**
   * 
   * @param reserve
   * a map from tradeables to MarketState. a marketstate is an id and a double.
   */
	public SimpleHighestBidderAllocation(Map<Tradeable, MarketState> reserve) {
		this.RESERVE = reserve;
	}
	
	
	@Override 
	//takes in an internal state
	public BidBundle getAllocation(MarketInternalState state) {
		System.out.println("BIDS? " + state.getBids());
		Map<Tradeable, MarketState> highest = new HashMap<Tradeable, MarketState>();
		MarketState def = new MarketState(null,0);
		for (Asset trade : state.getTradeables()) {
			MarketState maxBidder = this.RESERVE.getOrDefault(trade.getType(), def);
			for (Bid bid : state.getBids()) {
				if (bid.Bundle.getType().equals(BundleType.Simple)) {
					SimpleBidBundle bundle = (SimpleBidBundle) bid.Bundle; 
					MarketState bp = bundle.getBid(trade.getType());
					if (bp != null && bp.PRICE >= maxBidder.PRICE) {
						maxBidder = new MarketState(bid.AgentID, bp.PRICE);
					}
				} else {
					Logging.log("[X] Incorrect bundle type by " + bid.AgentID + " in auction " + bid.AuctionID);
				}
			}
			highest.put(trade.getType(), maxBidder);
		}
		return new SimpleBidBundle(highest);
	}
	@Override
	public void tick(long time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Map<Integer, Set<Asset>> getAllocations(Set<Bid> bids,
			Set<Asset> items) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public BidRequest getBidRequest(Set<Bid> bids, Integer iD) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isPrivate() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isOver() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public BundleType getBundleType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<Bid> withReserve(Set<Bid> bids) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isValid(Bid bid, Set<Bid> bids) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public MechanismType getAllocationType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public GameReport getReport() {
		// TODO Auto-generated method stub
		return null;
	}

}
