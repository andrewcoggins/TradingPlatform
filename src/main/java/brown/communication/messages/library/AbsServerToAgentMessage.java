package brown.communication.messages.library;

import brown.communication.messages.IServerToAgentMessage;
import brown.user.agent.IAgentBackend;

public abstract class AbsServerToAgentMessage extends AbsMessage implements IServerToAgentMessage {
  
  public AbsServerToAgentMessage(Integer messageID, Integer agentID) {
    super(messageID, agentID); 
  }
  
  public abstract void agentDispatch(IAgentBackend agent);
  
}
