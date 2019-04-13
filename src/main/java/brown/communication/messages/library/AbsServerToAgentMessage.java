package brown.communication.messages.library;

import brown.communication.messages.IServerToAgentMessage;
import brown.user.agent.IAgent;

public abstract class AbsServerToAgentMessage extends AbsMessage implements IServerToAgentMessage {

  private Integer agentID; 
  
  public AbsServerToAgentMessage(Integer messageID, Integer agentID) {
    super(messageID);
    this.agentID = agentID; 
  }

  public Integer getAgentID() {
    return this.agentID; 
  }
  
  public abstract void agentDispatch(IAgent agent);
  
}
