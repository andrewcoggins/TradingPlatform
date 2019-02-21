package brown.communication.channel.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.communication.bid.IBid;
import brown.communication.bid.library.OneSidedBidBundle;
import brown.communication.channel.IChannel;
import brown.communication.messages.library.TradeMessage;
import brown.logging.library.Logging;
import brown.platform.tradeable.ITradeable;
import brown.user.agent.library.AbsAgent;
import brown.user.agent.library.AbsAuctionAgent;

/*
 * Auction channel is the communication channel between the server and the agent in auctions.
 */
public class OneSidedChannel extends AbsChannel {
		
  /**
   * For Kryo
   * DO NOT USE
   */
	public OneSidedChannel() {
	  super();
	}

  /**
   * Constructor
   * @param ID
   */
	
	public OneSidedChannel(Integer ID) {
    super(ID);
	}

	@Override
	public void dispatchMessage(AbsAgent agent) {
		if (agent instanceof AbsAuctionAgent) {
        AbsAuctionAgent simpleSealedBidAgent = (AbsAuctionAgent) agent; 
        simpleSealedBidAgent.onSimpleSealed(this);
      }
	}

  @Override
  public void bid(AbsAgent agent, IBid bid) {
    OneSidedBidBundle c = (OneSidedBidBundle) bid; 
    Map<ITradeable, Double> fixedBids = new HashMap<ITradeable,Double>();    
    for (Entry<ITradeable, Double> b : c.bids.entrySet() ) {
      fixedBids.put(b.getKey(), b.getValue());
      if (fixedBids.size() > 2) {
        agent.CLIENT.sendTCP(new TradeMessage(0,this.ID,agent.ID, new OneSidedBidBundle(fixedBids)));
        fixedBids.clear(); 
      }
    }
    if (fixedBids.size() > 0) {
    TradeMessage toSend = (new TradeMessage(0,this.ID,agent.ID, new OneSidedBidBundle(fixedBids)));
      agent.CLIENT.sendTCP(toSend);
    }        
  }

  @Override
  public String toString() {
    return "SimpleAgentChannel [ID: " + this.ID +"]";
  }

}