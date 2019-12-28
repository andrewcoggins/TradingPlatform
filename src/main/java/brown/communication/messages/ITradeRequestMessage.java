package brown.communication.messages;

import brown.auction.marketstate.IMarketState;
import brown.platform.item.ICart;

public interface ITradeRequestMessage extends IServerToAgentMessage {
  
  public Integer getAgentID(); 
  
  public Integer getAuctionID();  
  
  public ICart getItems(); 
  
  public void addInformation(IMarketState publicState); 
  
}
