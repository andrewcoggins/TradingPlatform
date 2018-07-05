package brown.mechanism.channel;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.logging.Logging;
import brown.mechanism.bid.AuctionBid;
import brown.mechanism.bid.BidType;
import brown.mechanism.bidbundle.AuctionBidBundle;
import brown.mechanism.bidbundle.BundleType;
import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.tradeable.ITradeable;
import brown.platform.messages.TradeMessage;
import brown.user.agent.AbsAgent;
import brown.user.agent.AbsAuctionAgent;

/*
 * Auction channel is the communication channel between the server and the agent in auctions.
 */
public class SealedBidChannel extends AbsChannel {
		
  /**
   * For Kryo
   * DO NOT USE
   */
	public SealedBidChannel() {
	  super();
	}

  /**
   * Constructor
   * @param ID
   */
	public SealedBidChannel(Integer ID) {
    super(ID);
	}

	// this is not going to work out
	@Override
	public void dispatchMessage(AbsAgent agent) {
		if (agent instanceof AbsAuctionAgent) {
        AbsAuctionAgent simpleSealedBidAgent = (AbsAuctionAgent) agent; 
        simpleSealedBidAgent.onSimpleSealed(this);
      }
	}

  @Override
  public void bid(AbsAgent agent, IBidBundle bid) {
    AuctionBidBundle c = (AuctionBidBundle) bid; 
    if (bid.getType() == BundleType.AUCTION) {
      Map<ITradeable, BidType> fixedBids = new HashMap<ITradeable,BidType>();    
      for (Entry<ITradeable, BidType> b : ((AuctionBid) bid.getBids()).bids.entrySet() ) {
        fixedBids.put(b.getKey(), b.getValue());
        if (fixedBids.size() > 2) {
          agent.CLIENT.sendTCP(new TradeMessage(0,new AuctionBidBundle(fixedBids),this.ID,agent.ID));
          fixedBids.clear();
        }
      }
      if (fixedBids.size() > 0) {
        TradeMessage toSend = (new TradeMessage(0,new AuctionBidBundle(fixedBids),this.ID,agent.ID));
        agent.CLIENT.sendTCP(toSend);
      }     
    } else {
      Logging.log("[Channel encountered invalid bid type]");
      return;      
    }    
  }

  @Override
  public String toString() {
    return "SimpleAgentChannel [ID: " + this.ID +"]";
  }

  @Override
  public IAgentChannel sanitize(Integer agent, Map<Integer, Integer> privateToPublic) {
    return this;
  }
  
}