package brown.communication.messages;

import brown.platform.item.ICart;

public interface IBankUpdateMessage extends IServerToAgentMessage {

  public Integer getAgentID(); 
  
  public ICart getItemsAdded(); 
  
  public ICart getItemsLost();
  
  public Double getMoneyAddedLost(); 
  
}
