package brown.messages.library;

import java.util.HashMap;
import java.util.Map;

import brown.agent.AbsAgent;
import brown.bidbundle.IBidBundle;
import brown.channels.IAgentChannel;

/**
 * For each open market, the server then sends all agents a TradeRequest,
 * which describes the current market state: quotes for one-sided markets,
 * and orderBooks for two-sided markets. Whenever an agent wants, it can
 * respond to the TradeRequest by constructing and sending the server a
 * BidBundle (using the methods bid, buy, sell, etc.) for that market.
 * 
 * @author lcamery
 */
public class TradeRequestMessage extends AbsMessage {
  
	@Override
  public String toString() {
    return "TradeRequestMessage [MARKET=" + MARKET + ", reserve=" + reserve
        + ", idToSize=" + idToSize + "]";
  }

  public final IAgentChannel MARKET;
	// not used right now
	public final IBidBundle reserve;
	public final Map<Integer,Integer> idToSize;
	
	public TradeRequestMessage() {
		super(null);
		MARKET = null;
		reserve = null;
		idToSize = null;
	}

	/**
	 * 
	 * @param ID
	 * an ID for the trade request
	 * @param market
	 * the agent channel to be sent
	 * @param idToSize
	 * a map from agents' private IDs to the size of the auctions that they are bidding in. 
	 */
	public TradeRequestMessage(Integer ID, IAgentChannel market, Map<Integer, Integer> idToSize) {
		super(ID);
		this.MARKET = market;
		this.reserve = null;
		this.idToSize = idToSize;
	}

	@Override
	public void dispatch(AbsAgent agent) {	  
		this.MARKET.dispatchMessage(agent);
	}

	public TradeRequestMessage sanitize(Integer agent, Map<Integer,Integer> privateToPublic) {
	  Map<Integer,Integer> sanitizedMap = new HashMap<Integer,Integer>();
	  for (Integer id : idToSize.keySet()){
	    if (id.equals(agent)){
	      sanitizedMap.put(agent, idToSize.get(agent));
	    } else {
	      sanitizedMap.put(privateToPublic.get(agent), idToSize.get(agent));
	    }
	  }
	  return new TradeRequestMessage(this.ID, this.MARKET.sanitize(agent, privateToPublic), sanitizedMap);
	}	
}
