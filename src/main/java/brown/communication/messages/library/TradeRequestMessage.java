package brown.communication.messages.library;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.item.ICart;
import brown.user.agent.IAgent;

/**
 * Trade request message is sent by an open market 
 * to an agent to prompt bidding in that market. 
 * An agent typically sends a trademessage in response
 * to a TradeRequestMessage
 * @author andrew
 *
 */
public class TradeRequestMessage extends AbsServerToAgentMessage implements ITradeRequestMessage {

  private BidType bidType; 
  private ICart items; 
  private Integer auctionID; 
  
  public TradeRequestMessage() {
    super(null, null);
    this.bidType = null; 
    this.items = null; 
  }
  
  public TradeRequestMessage(Integer messageID, Integer auctionID, Integer agentID, BidType bidType, ICart items) {
    super(messageID, agentID);
    this.bidType = bidType; 
    this.items = items; 
    this.auctionID = auctionID; 
  }
  
  @Override
  public Integer getAuctionID() {
    return this.auctionID;
  }
  
  @Override
  public BidType getBidType() {
    return this.bidType; 
  }
  
  @Override
  public ICart getItems() {
    return this.items; 
  }

  @Override
  public void addInformation(IMarketPublicState publicState) {
    // TODO: 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    agent.onTradeRequestMessage(this);
  }

  @Override
  public String toString() {
    return "TradeRequestMessage [bidType=" + bidType + ", items=" + items
        + ", auctionID=" + auctionID + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((auctionID == null) ? 0 : auctionID.hashCode());
    result = prime * result + ((bidType == null) ? 0 : bidType.hashCode());
    result = prime * result + ((items == null) ? 0 : items.hashCode());
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
    if (auctionID == null) {
      if (other.auctionID != null)
        return false;
    } else if (!auctionID.equals(other.auctionID))
      return false;
    if (bidType != other.bidType)
      return false;
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }

}
