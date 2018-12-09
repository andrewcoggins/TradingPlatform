package brown.platform.messages.library;

import brown.mechanism.bid.IBid;
import brown.user.agent.library.AbsAgent;

/**
 * Trade message is sent by the agent to the server
 * specifying an action withing a trading environment
 * This may be a bid or some other action. 
 * @author andrew
 *
 */
public class TradeMessage extends AbsMessage {
  
	public final IBid action;
	public final Integer AuctionID;
	public final Integer AgentID;
	
	/**
	 * void kryo
	 */
	public TradeMessage() {
		super(null);
		this.AgentID = null;
		this.AuctionID = null;
		this.action = null;
	}

	/**
	 * Bid for when an agent wants to bid in an auction
	 * @param ID : bid ID
	 * @param bundle : bid bundle; varies by what the auction wants
	 * @param auctionID : auction's ID
	 * @param agentID : agent's ID; verified by server
	 */
	public TradeMessage(int ID, IBid action, Integer auctionID, Integer agentID) {
		super(ID);
		this.action = action;
		this.AuctionID = auctionID;
		this.AgentID = agentID;
	}


  @Override
  public void dispatch(AbsAgent agent) {
    // Noop
  }
	

  @Override
  public String toString() {
    return "TradeMessage [action=" + action + ", AuctionID=" + AuctionID
        + ", AgentID=" + AgentID + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((AgentID == null) ? 0 : AgentID.hashCode());
    result = prime * result + ((AuctionID == null) ? 0 : AuctionID.hashCode());
    result = prime * result + ((action == null) ? 0 : action.hashCode());
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
    TradeMessage other = (TradeMessage) obj;
    if (AgentID == null) {
      if (other.AgentID != null)
        return false;
    } else if (!AgentID.equals(other.AgentID))
      return false;
    if (AuctionID == null) {
      if (other.AuctionID != null)
        return false;
    } else if (!AuctionID.equals(other.AuctionID))
      return false;
    if (action == null) {
      if (other.action != null)
        return false;
    } else if (!action.equals(other.action))
      return false;
    return true;
  }
  
  
}
