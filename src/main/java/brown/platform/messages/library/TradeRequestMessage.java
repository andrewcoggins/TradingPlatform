package brown.platform.messages.library;

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.bidbundle.IBidBundle;
import brown.mechanism.channel.IAgentChannel;
import brown.user.agent.library.AbsAgent;

/**
 * Trade request message is sent by an open market 
 * to an agent to prompt bidding in that market. 
 * An agent typically sends a trademessage in response
 * to a TradeRequestMessage
 * @author andrew
 *
 */
public class TradeRequestMessage extends AbsMessage {

  public final IAgentChannel MARKET;
	public final IBidBundle reserve;
	public final Map<Integer,Integer> idToSize;
	
	/**
	 * void kryo 
	 */
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
	
	 @Override
	  public String toString() {
	    return "TradeRequestMessage [MARKET=" + MARKET + ", reserve=" + reserve
	        + ", idToSize=" + idToSize + "]";
	  }
}
