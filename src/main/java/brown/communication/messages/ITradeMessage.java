package brown.communication.messages;

import brown.communication.bid.IBidBundle;

public interface ITradeMessage extends IAgentToServerMessage {

  public IBidBundle getBid();
  
  public Integer getAuctionID();
  
  public Integer getAgentID();
  
}
