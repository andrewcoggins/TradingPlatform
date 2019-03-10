package brown.communication.messages;

import java.util.List;
import java.util.Map;

import brown.platform.tradeable.ITradeable;

public interface IBankUpdateMessage extends IServerToAgentMessage {

  public Integer getAgentID(); 
  
  public Map<String, List<ITradeable>> getTradeablesAdded(); 
  
  public Map<String, List<ITradeable>> getTradeablesLost();
  
  public Double getMoneyAddedLost(); 
  
  
}
