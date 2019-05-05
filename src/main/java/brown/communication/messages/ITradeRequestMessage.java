package brown.communication.messages;

import java.util.List;

import brown.communication.bid.library.BidType;

public interface ITradeRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public BidType getBidType(); 
  
  public List<String> getTradeableNames(); 
  
  
}
