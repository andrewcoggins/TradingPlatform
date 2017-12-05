package brown.channels.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.accounting.Ledger;
import brown.accounting.MarketState;
import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.SimpleBidBundle;
import brown.agent.AbsAgent;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;
import brown.messages.library.Bid;
import brown.setup.Logging;
import brown.todeprecate.PaymentType;
import brown.tradeable.library.Tradeable;

/*
 * Implements IMarket for Simple auctions
 */
public class SimpleAgentChannel implements IAgentChannel {
	private final Integer ID;
	private final Ledger LEDGER;
	private final SimpleBidBundle HIGHBID;
	private final int ELIGIBILITY;
	
	private final PaymentType PTYPE;
	private final MechanismType MTYPE;
	
	public SimpleAgentChannel() {
		this.ID = null;
		this.LEDGER = null;
		this.HIGHBID = null;
		this.PTYPE = null;
		this.MTYPE = null;
		this.ELIGIBILITY = 0;
	}
	
	/**
	 * Contructor
	 * @param ID
	 * @param ledger
	 * @param highBid
	 */
	public SimpleAgentChannel(Integer ID, Ledger ledger, PaymentType ptype, MechanismType mtype,
			SimpleBidBundle highBid, int elig) {
		if (highBid == null || ledger == null) {
			throw new IllegalArgumentException("Null structures");
		}
		this.ID = ID;
		this.LEDGER = ledger;
		this.HIGHBID = highBid;
		this.PTYPE = ptype;
		this.MTYPE = mtype;
		this.ELIGIBILITY = elig;
	}

	@Override
	public Ledger getLedger() {
		return this.LEDGER;
	}

	@Override
	public void dispatchMessage(AbsAgent agent) {
		switch(this.MTYPE) {
		case ContinuousDoubleAuction:
			break;
		case LMSR:
			break;
		case Lemonade:
			break;
		case OpenOutcry:
			agent.onSimpleOpenOutcry(this);
			break;
		case SealedBid:
			agent.onSimpleSealed(this);
			break;
		default:
			Logging.log("[X] unknown PaymentType");
			break;
		}
	}

	@Override
	public Integer getAuctionID() {
		return this.ID;
	}
	
	/**
	 * Returns the payment type
	 * @return
	 */
	public PaymentType getPaymentType() {
		return this.PTYPE;
	}
	
	/**
	 * Returns the high bid
	 * @return double
	 */
	public MarketState getMarketState(Tradeable type) {
		return this.HIGHBID.getBid(type);
	}
	
	/**
	 * Returns if this bundle maximizes revenue
	 * @return boolean
	 */
	public int getEligibility() {
		return this.ELIGIBILITY;
	}

	public void bid(AbsAgent agent, Map<Tradeable, Double> bids) {
		Map<Tradeable, MarketState> fixedBids = new HashMap<Tradeable,MarketState>();
		for (Entry<Tradeable, Double> bid : bids.entrySet()) {
			fixedBids.put(bid.getKey(), new MarketState(agent.ID, bid.getValue()));
			System.out.println("fixed bids " + fixedBids);
//			if (fixedBids.size() > 10) {
//				agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
//				fixedBids.clear();
//			}
		}
		if (fixedBids.size() > 0) {
			agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}

	public void demandSet(AbsAgent agent, Set<Tradeable> toBid) {
		Map<Tradeable, MarketState> fixedBids = new HashMap<Tradeable,MarketState>();
		for (Tradeable bid : toBid) {
			fixedBids.put(bid, new MarketState(agent.ID, 0));
			if (fixedBids.size() > 10) {
				agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
				fixedBids.clear();
			}
		}
		if (fixedBids.size() != 0) {
			agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}
	
	public void xorBid(AbsAgent agent, Map<Set<Tradeable>, Double> toBid) {
		if (3 < toBid.size()) {
			throw new IllegalArgumentException("Attempt to submit too many atomic bids");
		}
		
		Map<Tradeable, MarketState> fixedBids = new HashMap<Tradeable,MarketState>();
		for (Entry<Set<Tradeable>, Double> bid : toBid.entrySet()) {
			if (this.ELIGIBILITY < bid.getKey().size()) {
				throw new IllegalArgumentException("Attempt to submit ineligible bid " + bid.getKey());
			}
			for (Tradeable t : bid.getKey()) {
				fixedBids.put(t, new MarketState(agent.ID, bid.getValue()));
				if (fixedBids.size() > 10) {
					agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
					fixedBids.clear();
				}
			}
		}
		
		if (fixedBids.size() != 0) {
			agent.CLIENT.sendTCP(new Bid(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}
	
	public Set<Tradeable> getTradeables() {
		return this.HIGHBID.getBids().bids.keySet();
	}

}
