package brown.communication.messages;

import brown.communication.bid.library.BidType;
import brown.platform.information.IWhiteboard;
import brown.platform.item.ICart;

public interface ITradeRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public BidType getBidType(); 
  
  public ICart getItems(); 
  
  public void addInformation(IWhiteboard whiteboard); 
  
}
