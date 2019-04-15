package brown.communication.messages.library;

import java.util.List;

import brown.communication.bid.library.BidType;
import brown.communication.messages.ITradeRequestMessage;
import brown.platform.whiteboard.IWhiteboard;
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
  private List<String> tradeableNames; 
  
  public TradeRequestMessage() {
    super(null, null);
    this.bidType = null; 
    this.tradeableNames = null; 
  }
  
  public TradeRequestMessage(Integer messageID, Integer agentID, BidType bidType, List<String> tradeableNames) {
    super(messageID, agentID);
    this.bidType = bidType; 
    this.tradeableNames = tradeableNames; 
  }
  
  
  public BidType getBidType() {
    return this.bidType; 
  }
  
  public List<String> getTradeableTypes() {
    return this.tradeableNames; 
  }

  public void addInformation(IWhiteboard whiteboard) {
    // TODO: 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String toString() {
    return "TradeRequestMessage [bidType=" + bidType + ", tradeableNames="
        + tradeableNames + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bidType == null) ? 0 : bidType.hashCode());
    result = prime * result
        + ((tradeableNames == null) ? 0 : tradeableNames.hashCode());
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
    if (bidType != other.bidType)
      return false;
    if (tradeableNames == null) {
      if (other.tradeableNames != null)
        return false;
    } else if (!tradeableNames.equals(other.tradeableNames))
      return false;
    return true;
  }

}
