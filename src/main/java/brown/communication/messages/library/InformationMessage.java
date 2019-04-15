package brown.communication.messages.library;

import brown.communication.messages.IInformationMessage;
import brown.platform.whiteboard.IWhiteboard;
import brown.user.agent.IAgent;

public class InformationMessage extends AbsServerToAgentMessage implements IInformationMessage {

  public InformationMessage(int messageID, int agentID, IWhiteboard whiteboard) {
    super(messageID, agentID); 
  }
  
  @Override
  public void agentDispatch(IAgent agent) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public Integer getMessageID() {
    // TODO Auto-generated method stub
    return null;
  }

}
