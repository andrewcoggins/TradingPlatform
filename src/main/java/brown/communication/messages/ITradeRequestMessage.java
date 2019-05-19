package brown.communication.messages;

import brown.communication.bid.library.BidType;
import brown.platform.item.ICart;
import brown.platform.whiteboard.IWhiteboard;

public interface ITradeRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public BidType getBidType(); 
  
  public ICart getItems(); 
  
  public void addInformation(IWhiteboard whiteboard); 
  
}
