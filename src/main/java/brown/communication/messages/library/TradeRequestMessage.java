package brown.communication.messages.library;

import brown.auction.marketstate.IMarketPublicState;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.item.ICart;
import brown.user.agent.IAgentBackend;

/**
 * Trade request message is sent by an open market 
 * to an agent to prompt bidding in that market. 
 * An agent typically sends a trademessage in response
 * to a TradeRequestMessage
 * @author andrew
 *
 */
public class TradeRequestMessage extends AbsServerToAgentMessage implements ITradeRequestMessage {

  private ICart items; 
  private Integer auctionID; 
  
  public TradeRequestMessage() {
    super(null, null);
    this.items = null; 
  }
  
  // TODO: add a price? 
  public TradeRequestMessage(Integer messageID, Integer auctionID, Integer agentID, ICart items) {
    super(messageID, agentID);
    this.items = items; 
    this.auctionID = auctionID; 
  }
  
  @Override
  public Integer getAuctionID() {
    return this.auctionID;
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
  public void agentDispatch(IAgentBackend agent) { 
    agent.onTradeRequestMessage(this);
  }
  
  @Override
  public String toString() {
    return "TradeRequestMessage [items=" + items + ", auctionID=" + auctionID
        + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((auctionID == null) ? 0 : auctionID.hashCode());
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
    if (items == null) {
      if (other.items != null)
        return false;
    } else if (!items.equals(other.items))
      return false;
    return true;
  }


}
