package brown.channels.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.agent.AbsAgent;
import brown.agent.AbsSimpleSealedAgent;
import brown.bid.interim.BidType;
import brown.bid.library.AuctionBid;
import brown.bidbundle.BundleType;
import brown.bidbundle.IBidBundle;
import brown.bidbundle.library.AuctionBidBundle;
import brown.channels.IAgentChannel;
import brown.logging.Logging;
import brown.messages.library.TradeMessage;
import brown.tradeable.ITradeable;

/*
 * Auction channel is the communication channel between the server and the agent in auctions.
 */
public class AuctionChannel extends AbsChannel {
		
  /**
   * For Kryo
   * DO NOT USE
   */
	public AuctionChannel() {
	  super();
	}

  /**
   * Constructor
   * @param ID
   */
	public AuctionChannel(Integer ID) {
    super(ID);
	}

	@Override
	public void dispatchMessage(AbsAgent agent) {
		if (agent instanceof AbsSimpleSealedAgent) {
        AbsSimpleSealedAgent simpleSealedBidAgent = (AbsSimpleSealedAgent) agent; 
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