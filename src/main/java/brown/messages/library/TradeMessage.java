package brown.messages.library;

import brown.agent.AbsAgent;
import brown.bidbundle.IBidBundle;

public class TradeMessage extends AbsMessage {
  
	public final IBidBundle Bundle;
	public final Integer AuctionID;
	public final Integer AgentID;
	
	public TradeMessage() {
		super(null);
		this.AgentID = null;
		this.AuctionID = null;
		this.Bundle = null;
	}

	/**
	 * Bid for when an agent wants to bid in an auction
	 * @param ID : bid ID
	 * @param bundle : bid bundle; varies by what the auction wants
	 * @param auctionID : auction's ID
	 * @param agentID : agent's ID; verified by server
	 */
	public TradeMessage(int ID, IBidBundle bundle, Integer auctionID, Integer agentID) {
		super(ID);
		this.Bundle = bundle;
		this.AuctionID = auctionID;
		this.AgentID = agentID;
	}
	
	/**
	 * Safe copy for server during ID verification
	 * @param agentID : truthful ID
	 * @return Bid
	 */
	public TradeMessage safeCopy(Integer agentID) {
		return new TradeMessage(this.ID, this.Bundle, this.AuctionID, agentID);
	}

  @Override
  public void dispatch(AbsAgent agent) {
    // Noop
  }
	
  @Override
  public String toString() {
    return "[" + this.AuctionID + ":" + this.AgentID + "->" + this.Bundle + "]";
  }
  
	@Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((AgentID == null) ? 0 : AgentID.hashCode());
    result = prime * result + ((AuctionID == null) ? 0 : AuctionID.hashCode());
    result = prime * result + ((Bundle == null) ? 0 : Bundle.hashCode());
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
    if (Bundle == null) {
      if (other.Bundle != null)
        return false;
    } else if (!Bundle.equals(other.Bundle))
      return false;
    return true;
  }
	
}
