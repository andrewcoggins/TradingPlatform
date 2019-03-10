package brown.communication.messages;

import brown.communication.bid.IBid;

public interface ITradeMessage extends IAgentToServerMessage {

  public IBid getBid();
  
  public Integer getAuctionID();
  
  public Integer getAgentID();
  
}
