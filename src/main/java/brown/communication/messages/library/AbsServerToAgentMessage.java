package brown.communication.messages.library;

import brown.communication.messages.IServerToAgentMessage;
import brown.user.agent.IAgent;

public abstract class AbsServerToAgentMessage extends AbsMessage implements IServerToAgentMessage {

  public AbsServerToAgentMessage(Integer messageID) {
    super(messageID);
    // TODO Auto-generated constructor stub
  }

  public abstract void agentDispatch(IAgent agent);
  
}
