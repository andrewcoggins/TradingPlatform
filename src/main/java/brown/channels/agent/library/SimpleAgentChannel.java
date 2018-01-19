package brown.channels.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.accounting.bid.AuctionBid;
import brown.accounting.bidbundle.IBidBundle;
import brown.accounting.bidbundle.library.AuctionBidBundle;
import brown.accounting.bidbundle.library.BundleType;
import brown.accounting.library.Ledger;
import brown.agent.AbsAgent;
import brown.agent.AbsOpenOutcryAgent;
import brown.agent.AbsSimpleSealedBidAgent;
import brown.channels.MechanismType;
import brown.messages.library.TradeMessage;
import brown.setup.Logging;
import brown.tradeable.ITradeable;

/*
 * Implements IMarket for Simple auctions
 */
public class SimpleAgentChannel extends AbsChannel {
  
	private final AuctionBidBundle HighBid;
  private final MechanismType MechType;	
		
	public SimpleAgentChannel() {
	  super();
		this.HighBid = null;
		this.MechType = null;
	}

  /**
	 * Contructor
	 * @param ID
	 * @param ledger
	 * @param highBid
	 */
	public SimpleAgentChannel(Integer ID, Ledger ledger, MechanismType mtype, AuctionBidBundle highBid) {
    super(ID, ledger);	  
		this.HighBid = highBid;
		this.MechType = mtype;
	}

	@Override
	public void dispatchMessage(AbsAgent agent) {
		switch(this.MechType) {
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

	/**
	 * Returns the high bid
	 * @return double
	 */
	public double getHighBId(ITradeable t) {
		return this.HighBid.getBids().bids.get(t);
	}

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    if (bid.getType() == BundleType.AUCTION){
      Map<ITradeable, Double> fixedBids = new HashMap<ITradeable,Double>();    
      for (Entry<ITradeable, Double> b : ((AuctionBid) bid.getBids()).bids.entrySet() ){
        fixedBids.put(b.getKey(), b.getValue());
        if (fixedBids.size() > 10) {
          agent.CLIENT.sendTCP(new TradeMessage(0,new AuctionBidBundle(fixedBids),this.ID,agent.ID));
          fixedBids.clear();
        }
      }
      if (fixedBids.size() > 0) {
        agent.CLIENT.sendTCP(new TradeMessage(0,new AuctionBidBundle(fixedBids),this.ID,agent.ID));
      }     
    } else {
      Logging.log("[Channel encountered invalid bid type]");
      return;      
    }    
  }

  @Override
  public String toString() {
    return "SimpleAgentChannel [HighBid=" + HighBid + ", MechType=" + MechType
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((HighBid == null) ? 0 : HighBid.hashCode());
    result = prime * result + ((MechType == null) ? 0 : MechType.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    return (obj instanceof SimpleAgentChannel && 
        ((SimpleAgentChannel) obj).HighBid.equals(this.HighBid) &&
        ((SimpleAgentChannel) obj).ID.equals(this.ID) &&
        ((SimpleAgentChannel) obj).ledger.equals(this.ledger) &&
        ((SimpleAgentChannel) obj).MechType.equals(this.MechType));
 }
}