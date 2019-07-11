package brown.communication.messages.library;

import brown.communication.messages.IInformationMessage;
import brown.platform.information.IWhiteboard;
import brown.user.agent.IAgent;

public class InformationMessage extends AbsServerToAgentMessage implements IInformationMessage {

  public InformationMessage(int messageID, int agentID, IWhiteboard whiteboard) {
    super(messageID, agentID); 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    agent.onInformationMessage(this);
  }

}
