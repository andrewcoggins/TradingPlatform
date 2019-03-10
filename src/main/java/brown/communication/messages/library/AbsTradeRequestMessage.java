package brown.communication.messages.library;

import brown.communication.messages.ITradeRequestMessage;

public abstract class AbsTradeRequestMessage extends AbsServerToAgentMessage implements ITradeRequestMessage {
  
  private Integer agentID; 
  
  public AbsTradeRequestMessage() {
    super(null); 
  }
  
  public AbsTradeRequestMessage(Integer messageID, Integer agentID) {
    super(messageID);
    this.agentID = agentID; 
  }
  
  public Integer getAgentID() {
    return this.agentID; 
  }
  
}
