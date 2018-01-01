package brown.messages.library;

import java.util.Comparator;

import brown.accounting.bidbundle.AbsBidBundle;
import brown.accounting.bidbundle.IBidBundle;
import brown.agent.AbsAgent;
import brown.messages.AbsMessage;

public class BidMessage extends AbsMessage {
	public final AbsBidBundle Bundle;
	public final Integer AuctionID;
	public final Integer AgentID;
	
	/**
	 * Empty constructor for kryo
	 * DO NOT USE
	 */
	public BidMessage() {
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
	public BidMessage(int ID, AbsBidBundle bundle, Integer auctionID, Integer agentID) {
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
	public BidMessage safeCopy(Integer agentID) {
		return new BidMessage(this.ID, this.Bundle, this.AuctionID, agentID);
	}
	
	public static class BidComparator implements Comparator<BidMessage> {
		private final boolean ASC;
		
		public BidComparator(boolean ascending) {
			this.ASC = ascending;
		}

		@Override
		public int compare(BidMessage o1, BidMessage o2) {
			if (this.ASC) {
				return new Double(o1.Bundle.getCost()).compareTo(new Double(o2.Bundle.getCost()));
			} else {
				return new Double(o2.Bundle.getCost()).compareTo(new Double(o1.Bundle.getCost()));
			}
		}
		
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
    BidMessage other = (BidMessage) obj;
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

	@Override
	public void dispatch(AbsAgent agent) {
		//Noop
	}
	
	@Override
	public String toString() {
		return "[" + this.AuctionID + ":" + this.AgentID + "->" + this.Bundle + "]";
	}
}
