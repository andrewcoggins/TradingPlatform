package brown.channels.agent.library;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import brown.agent.AbsAgent;
import brown.agent.AbsSSSPAgent;
import brown.bid.bidbundle.BundleType;
import brown.bid.bidbundle.IBidBundle;
import brown.bid.bidbundle.library.AuctionBidBundle;
import brown.bid.library.AuctionBid;
import brown.messages.library.TradeMessage;
import brown.setup.Logging;
import brown.tradeable.ITradeable;

/*
 * Implements IMarket for Simple auctions
 */
public class SSSPChannel extends AbsChannel {
		
  /**
   * For Kryo
   * DO NOT USE
   */
	public SSSPChannel() {
	  super();
	}

  /**
   * Constructor
   * @param ID
   */
	public SSSPChannel(Integer ID) {
    super(ID);
	}

	@Override
	public void dispatchMessage(AbsAgent agent) {
		if (agent instanceof AbsSSSPAgent) {
        AbsSSSPAgent simpleSealedBidAgent = (AbsSSSPAgent) agent; 
        simpleSealedBidAgent.onSSSP(this);
      }
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
    return "SimpleAgentChannel [ID: " + this.ID +"]";
  }
  
}