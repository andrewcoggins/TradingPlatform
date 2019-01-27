package brown.mechanism.messages.library;

import java.util.HashMap;
import java.util.Map;

import brown.mechanism.bid.IBid;
import brown.mechanism.channel.IChannel;
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

  public final IChannel MARKET;
	public final IBid reserve;
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
	public TradeRequestMessage(Integer ID, IChannel market, Map<Integer, Integer> idToSize) {
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
	  return new TradeRequestMessage(this.ID, this.MARKET, sanitizedMap);
	}

  @Override
  public String toString() {
    return "TradeRequestMessage [MARKET=" + MARKET + ", reserve=" + reserve
        + ", idToSize=" + idToSize + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((MARKET == null) ? 0 : MARKET.hashCode());
    result = prime * result + ((idToSize == null) ? 0 : idToSize.hashCode());
    result = prime * result + ((reserve == null) ? 0 : reserve.hashCode());
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
    TradeRequestMessage other = (TradeRequestMessage) obj;
    if (MARKET == null) {
      if (other.MARKET != null)
        return false;
    } else if (!MARKET.equals(other.MARKET))
      return false;
    if (idToSize == null) {
      if (other.idToSize != null)
        return false;
    } else if (!idToSize.equals(other.idToSize))
      return false;
    if (reserve == null) {
      if (other.reserve != null)
        return false;
    } else if (!reserve.equals(other.reserve))
      return false;
    return true;
  }	
  
}
