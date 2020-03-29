package brown.communication.messages.library;

import com.esotericsoftware.kryonet.Connection;

import brown.communication.bid.IBidBundle;
import brown.communication.messages.ITradeMessage;
import brown.communication.messageserver.IOfflineMessageServer;
import brown.communication.messageserver.IOnlineMessageServer;
import brown.user.agent.IAgentBackend;

/**
 * Trade message is sent by the agent to the server
 * specifying an action withing a trading environment
 * This may be a bid or some other action. 
 * @author andrew
 *
 */
public class TradeMessage extends AbsAgentToServerMessage implements ITradeMessage {
  
  private Integer auctionID; 
  private IBidBundle bid; 
  
  public TradeMessage() {
    super(null, null); 
  }
  
  public TradeMessage(Integer messageID, Integer agentID, Integer responseID, Integer auctionID, IBidBundle bid) {
	    super(messageID ,agentID, responseID); 
	    this.auctionID = auctionID; 
	    this.bid = bid; 
	  }
  
  public TradeMessage(Integer messageID, Integer agentID, Integer auctionID, IBidBundle bid) {
    this(messageID ,agentID, null, auctionID, bid); 
  }
  
  public Integer getAgentID() {
    return this.agentID; 
  }
  
  public Integer getAuctionID() {
    return this.auctionID; 
  }
  
  public IBidBundle getBid() {
    return this.bid; 
  }
  
  @Override
  public void serverDispatch(Connection connection, IOnlineMessageServer server) {
    server.onBid(connection, this);
  }

  @Override
  public String toString() {
    return "TradeMessage [bid=" + bid + ", auctionID=" + auctionID
        + ", agentID=" + agentID + "]";
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((agentID == null) ? 0 : agentID.hashCode());
    result = prime * result + ((auctionID == null) ? 0 : auctionID.hashCode());
    result = prime * result + ((bid == null) ? 0 : bid.hashCode());
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
    if (agentID == null) {
      if (other.agentID != null)
        return false;
    } else if (!agentID.equals(other.agentID))
      return false;
    if (auctionID == null) {
      if (other.auctionID != null)
        return false;
    } else if (!auctionID.equals(other.auctionID))
      return false;
    if (bid == null) {
      if (other.bid != null)
        return false;
    } else if (!bid.equals(other.bid))
      return false;
    return true;
  }

@Override
public void offlineServerDispatch(IAgentBackend connection, IOfflineMessageServer server) {
	server.onBid(connection, this);
}

  
}
