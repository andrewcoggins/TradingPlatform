package brown.channels.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import brown.accounting.Ledger;

import brown.accounting.bid.SimpleBid;
import brown.accounting.bidbundle.library.SimpleBidBundle;
import brown.agent.AbsAgent;
import brown.agent.AbsLemonadeAgent;
import brown.agent.AbsOpenOutcryAgent;
import brown.agent.AbsSimpleSealedBidAgent;
import brown.channels.MechanismType;
import brown.channels.agent.IAgentChannel;
import brown.messages.library.TradeMessage;
import brown.setup.Logging;
import brown.todeprecate.PaymentType;
import brown.tradeable.ITradeable;
import brown.tradeable.library.MultiTradeable;

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
		case CDA:
			break;
		case LMSR:
			break;
		case Lemonade:
			break;
		case OpenOutcry:
	    if (agent instanceof AbsOpenOutcryAgent) {
	      AbsOpenOutcryAgent openOutcryAgent = (AbsOpenOutcryAgent) agent; 
	      openOutcryAgent.onSimpleOpenOutcry(this);
	    };
			break;
		case SealedBid:
      if (agent instanceof AbsSimpleSealedBidAgent) {
        AbsSimpleSealedBidAgent simpleSealedBidAgent = (AbsSimpleSealedBidAgent) agent; 
        simpleSealedBidAgent.onSimpleSealedBid(this);
      };
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
	public double getMarketState(MultiTradeable t) {
		return this.HIGHBID.getBids().bids.get(t);
	}
	
	/**
	 * Returns if this bundle maximizes revenue
	 * @return boolean
	 */
	public int getEligibility() {
		return this.ELIGIBILITY;
	}

	public void bid(AbsAgent agent, Map<MultiTradeable, Double> bids) {
		Map<ITradeable, Double> fixedBids = new HashMap<ITradeable,Double>();
		for (Entry<ITradeable, Double> bid : bids.entrySet()) {
			fixedBids.put(bid.getKey(), bid.getValue());
			if (fixedBids.size() > 10) {
				agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
				fixedBids.clear();
			}
		}
		if (fixedBids.size() > 0) {
			agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}

	public void demandSet(AbsAgent agent, Set<MultiTradeable> toBid) {
		Map<MultiTradeable, Double> fixedBids = new HashMap<MultiTradeable,Double>();
		for (MultiTradeable bid : toBid) {
			fixedBids.put(bid, 0.);
			if (fixedBids.size() > 10) {
				agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
				fixedBids.clear();
			}
		}
		if (fixedBids.size() != 0) {
			agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}
	
	public void xorBid(AbsAgent agent, Map<Set<MultiTradeable>, Double> toBid) {
		if (3 < toBid.size()) {
			throw new IllegalArgumentException("Attempt to submit too many atomic bids");
		}
		
		Map<MultiTradeable, Double> fixedBids = new HashMap<MultiTradeable,Double>();
		for (Entry<Set<MultiTradeable>, Double> bid : toBid.entrySet()) {
			if (this.ELIGIBILITY < bid.getKey().size()) {
				throw new IllegalArgumentException("Attempt to submit ineligible bid " + bid.getKey());
			}
			for (MultiTradeable t : bid.getKey()) {
				fixedBids.put(t, bid.getValue());
				if (fixedBids.size() > 10) {
					agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
					fixedBids.clear();
				}
			}
		}
		
		if (fixedBids.size() != 0) {
			agent.CLIENT.sendTCP(new TradeMessage(0,new SimpleBidBundle(fixedBids),this.ID,agent.ID));
		}
	}
	
	public Set<MultiTradeable> getTradeables() {
		return this.HIGHBID.getBids().bids.keySet();
	}
	
	//toString
	
  @Override
	public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + ELIGIBILITY;
	  result = prime * result + ((HIGHBID == null) ? 0 : HIGHBID.hashCode());
	  result = prime * result + ((ID == null) ? 0 : ID.hashCode());
	  result = prime * result + ((LEDGER == null) ? 0 : LEDGER.hashCode());
	  result = prime * result + ((MTYPE == null) ? 0 : MTYPE.hashCode());
	  result = prime * result + ((PTYPE == null) ? 0 : PTYPE.hashCode());
	  return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    SimpleAgentChannel other = (SimpleAgentChannel) obj;
	    if (ELIGIBILITY != other.ELIGIBILITY)
	      return false;
	    if (HIGHBID == null) {
	      if (other.HIGHBID != null)
	        return false;
	    } else if (!HIGHBID.equals(other.HIGHBID))
	      return false;
	    if (ID == null) {
	      if (other.ID != null)
	        return false;
	    } else if (!ID.equals(other.ID))
	      return false;
	    if (LEDGER == null) {
	      if (other.LEDGER != null)
	        return false;
	    } else if (!LEDGER.equals(other.LEDGER))
	      return false;
	    if (MTYPE != other.MTYPE)
	      return false;
	    if (PTYPE != other.PTYPE)
	      return false;
	    return true;
	  }
	  
}