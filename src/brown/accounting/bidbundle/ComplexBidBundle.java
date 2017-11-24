package brown.accounting.bidbundle;

import java.util.Map;
import java.util.Set;

import brown.accounting.BundleType;
import brown.accounting.MarketState;
import brown.accounting.bid.ComplexBid;
import brown.tradeable.library.Tradeable;



/**
 * A Complex Bid Bundle is a bid bundle that submits bids for vectors 
 * of goods. i.e. (a b c) = 1
 * @author acoggins
 *
 */
public class ComplexBidBundle extends AbsBidBundle {
	private final ComplexBid BIDS;
	private final BundleType BT;
	
	/**
	 * Empty constructor for kryo net
	 */
	public ComplexBidBundle() {
		this.BIDS = null;
		this.BT = null;
	}
	
	/**
	 * Use this constructor
	 * ComplexBidBundle uses Set<FullType>
	 * @param bid : agent's bid
	 * @param agent : agent ID
	 */
	public ComplexBidBundle(ComplexBid bid, Integer agent) {
		this.BIDS = bid;
		this.BT = BundleType.Complex;
	}

	@Override
	public double getCost() {
		double max = 0;
		for (MarketState b : this.BIDS.bids.values()) {
			max = Math.max(b.PRICE, max);
		}
		return max;
	}

	@Override
	public IBidBundle wipeAgent(Integer ID) {
		return new ComplexBidBundle(this.BIDS, ID);
	}

	@Override
	public BundleType getType() {
		return this.BT;
	}
	
	public ComplexBid getBids() {
		return this.BIDS;
	}

}
